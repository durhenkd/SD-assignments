package tiberiu.assignment2.bussiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiberiu.assignment2.model.*;
import tiberiu.assignment2.persistence.*;
import tiberiu.assignment2.web.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public UserService(UserRepository userRepository, RestaurantRepository restaurantRepository, OrderRepository orderRepository, MenuItemRepository menuItemRepository, OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;

        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    /** Get the menu of the restaurant with the given id
     * @param id - id of the restaurant
     * @return List of MenuItem if restaurant is found, null otherwise
     * @see MenuItem
     * @see Restaurant
     */
    public List<MenuItem> getMenu(Long id) {
        Optional<Restaurant> r = restaurantRepository.findById(id);
        if (r.isEmpty()) {
            log.error("Restaurant not found for id " + id);
            return null;
        }
        return r.get().getItems();
    }

    /** Get user's orders
     * @param id - id of the user
     * @return List of UserOrder if user is found, throws a Runtime exception otherwise
     */
    public List<UserOrder> getOrders(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isEmpty()){
            log.error("Restaurant not found for id " + id);
            throw new RuntimeException("ERROR: user with id" + id + " doesn't exist");
        }

        log.error("Orders retrieved for user id: " + id);
        return maybeUser.get().getOrders();
    }


    /** Adds the order to the user's orders
     * @param id - the user's id
     * @param dto - an OrderDTO object containing all the necessary information
     * @see OrderDTO
     */
    public void addOrder(Long id, OrderDTO dto) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);
        if (maybeRestaurant.isEmpty())
            throw new RuntimeException("ERROR: restaurant with id" + id + " doesn't exist");

        Restaurant restaurant = maybeRestaurant.get();

        Optional<User> maybeUser = userRepository.findById(dto.getUserId());
        if (maybeUser.isEmpty())
            throw new RuntimeException("ERROR: user with id" + id + " doesn't exist");

        User user = maybeUser.get();

        for (int index = 0; index < dto.getItemIdList().size(); ++index) {
            Long item_id = Long.valueOf(dto.getItemIdList().get(index));

            Optional<MenuItem> maybeMenuItem = menuItemRepository.findById(item_id);
            if (maybeMenuItem.isEmpty())
                throw new RuntimeException("ERROR: menuItem with id " + item_id + " doesn't exist");

            OrderItem orderItem = orderItemRepository.save(new OrderItem(maybeMenuItem.get(), dto.getQuantityList().get(index)));
            orderItems.add(orderItem);
        }

        UserOrder userOrder = new UserOrder(orderItems, OrderStatus.PENDING, restaurant);
        userOrder = orderRepository.save(userOrder);

        user.getOrders().add(userOrder);
        userRepository.save(user);
    }
}
