package tiberiu.assignment2.bussiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiberiu.assignment2.model.*;
import tiberiu.assignment2.persistence.AdminRepository;
import tiberiu.assignment2.persistence.MenuItemRepository;
import tiberiu.assignment2.persistence.OrderRepository;
import tiberiu.assignment2.persistence.RestaurantRepository;
import tiberiu.assignment2.web.dto.MenuItemDTO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository,
                        MenuItemRepository menuItemRepository) {
        this.adminRepository = adminRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }


    /***
     * Attempt to get the Orders belonging to the restaurant with the given id
     * @param id - id of the restaurant/admin
     * @return the List containing all the Orders, it may be empty if there are no orders,
     * null is returned if the restaurant doesn't exist
     * @see UserOrder
     */
    public List<UserOrder> getOrders(Long id) {
        Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);

        if (maybeRestaurant.isEmpty()) {
            log.error("Restaurant with id " + id + " not found");
            throw new RuntimeException("Restaurant with id " + id + " not found");
        }

        Restaurant r = maybeRestaurant.get();
        return orderRepository.findAllByRestaurant(r);
    }

    /***
     * This is private helper function for other functions. It throws a RuntimeException if not successful.
     * @param restaurantID - id of the restaurant/admin
     * @param orderID - id of the order
     * @return The Order with the orderId id belonging to restaurantID
     * @see UserOrder
     */
    private UserOrder acceptRejectOrderHelper(Long restaurantID, Long orderID) {
        Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(restaurantID);
        if (maybeRestaurant.isEmpty()) {
            log.error("ERROR: restaurant with id" + restaurantID + " doesn't exist");
            throw new RuntimeException("ERROR: restaurant with id" + restaurantID + " doesn't exist");
        }


        Restaurant restaurant = maybeRestaurant.get();

        Optional<UserOrder> maybeUserOrder = orderRepository.findById(orderID);
        if (maybeUserOrder.isEmpty()) {
            log.error("ERROR: order with id" + orderID + " doesn't exist");
            throw new RuntimeException("ERROR: order with id" + orderID + " doesn't exist");
        }


        UserOrder userOrder = maybeUserOrder.get();
        if (!Objects.equals(userOrder.getRestaurant().getId(), restaurantID)) {
            log.error("ERROR: order with restaurant id" + userOrder.getRestaurant().getId() + " doesn't match the given restaurant id " + restaurantID);
            throw new RuntimeException("ERROR: order with restaurant id" + userOrder.getRestaurant().getId() + " doesn't match the given restaurant id " + restaurantID);
        }


        return userOrder;
    }

    /***
     * It changes the status of the given order belonging to the given restaurant
     * according to the accept sequence (PENDING->ACCEPTED->IN_DELIVERY->DELIVERED).</br>
     * It throws a RuntimeException if the order has status DECLINED.
     * @see OrderStatus
     * @param restaurantID - id of the restaurant/admin
     * @param orderID - id of the order
     */
    public void acceptOrder(Long restaurantID, Long orderID) {

        UserOrder userOrder = acceptRejectOrderHelper(restaurantID, orderID);
        try {
            userOrder.setStatus(OrderStatus.accept(userOrder.getStatus()));
            orderRepository.save(userOrder);
            log.info("Order Accepted");
        } catch (Exception e) {
            log.error("Wrong Status flow");
        }
    }

    /***
     * It declines the order (status changed to DECLINED).</br>
     * It throws a RuntimeException if status is not PENDING, or already DECLINED.
     * @param restaurantID - id of the restaurant/admin
     * @param orderID - id of the order
     */
    public void declineOrder(Long restaurantID, Long orderID) {

        UserOrder userOrder = acceptRejectOrderHelper(restaurantID, orderID);
        try {
            userOrder.setStatus(OrderStatus.refuse(userOrder.getStatus()));
            orderRepository.save(userOrder);
            log.info("Order Declined");
        } catch (Exception e) {
            log.error("Wrong Status flow");
        }
    }

    /***
     * Adds a food to the menu of the restaurant corresponding to the id
     * @param id - id of the restaurant/admin
     * @param itemDto - object containing all the information necessary to persist a menu item
     * @see MenuItemDTO
     * @see MenuItem
     * @return
     */
    public MenuItem addItem(Long id, MenuItemDTO itemDto) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isEmpty()) return null;
        MenuItem item = new MenuItem(itemDto.getName(), itemDto.getDescription(), itemDto.getImageUrl(), itemDto.getFoodCategory(), itemDto.getPrice());
        menuItemRepository.save(item);
        Restaurant r = admin.get().getManagedRestaurant();
        r.getItems().add(item);
        restaurantRepository.save(r);
        return item;
    }

}
