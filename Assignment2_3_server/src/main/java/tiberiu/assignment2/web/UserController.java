package tiberiu.assignment2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiberiu.assignment2.bussiness.RestaurantService;
import tiberiu.assignment2.bussiness.UserService;
import tiberiu.assignment2.model.Restaurant;
import tiberiu.assignment2.web.dto.ErrorDTO;
import tiberiu.assignment2.web.dto.OrderDTO;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final RestaurantService restaurantService;

    @Autowired
    public UserController(UserService us, RestaurantService restaurantService) {
        this.userService = us;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("This is a test");
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok().body(userService.getAllRestaurants());
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getRestaurant(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null)
            return ResponseEntity.internalServerError().body(
                    new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.name(),
                            "Failed to fetch Restaurant")
            );
        return ResponseEntity.ok().body(restaurant);
    }

    @GetMapping("/restaurant/{id}/menu")
    public ResponseEntity<?> getMenu(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.getMenu(id));
    }

    @PostMapping("/restaurant/{id}")
    public ResponseEntity<?> postOrder(@PathVariable("id") Long id, @RequestBody OrderDTO order) {
        userService.addOrder(id, order);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Long userId) {
        return ResponseEntity.ok().body(userService.getOrders(userId));
    }
}
