package tiberiu.assignment2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiberiu.assignment2.bussiness.AdminService;
import tiberiu.assignment2.bussiness.RestaurantService;
import tiberiu.assignment2.model.MenuItem;
import tiberiu.assignment2.model.Restaurant;
import tiberiu.assignment2.model.UserOrder;
import tiberiu.assignment2.web.dto.MenuItemDTO;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final RestaurantService restaurantService;

    @Autowired
    public AdminController(AdminService adminService, RestaurantService restaurantService) {
        this.adminService = adminService;
        this.restaurantService = restaurantService;
    }

    //TODO revizuie
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuItem>>getMenu(@PathVariable("id") Long id){
        Restaurant r = restaurantService.getRestaurant(id);
        if( r == null ) return ResponseEntity.internalServerError().body(null);
        return ResponseEntity.ok().body(r.getItems());
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<UserOrder>>getOrders(@PathVariable("id") Long id){
        List<UserOrder> list = adminService.getOrders(id);
        if( list == null ) return ResponseEntity.internalServerError().body(null);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/{id}/orders/{ordId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable("id") Long id, @PathVariable("ordId") Long orderId){
        adminService.acceptOrder(id, orderId);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/{id}/orders/{ordId}/reject")
    public ResponseEntity<?> rejectOrder(@PathVariable("id") Long id, @PathVariable("ordId") Long orderId){
        adminService.declineOrder(id, orderId);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<?>addMenuItem(@PathVariable("id") Long id, @RequestBody MenuItemDTO item){
        MenuItem menuItem = adminService.addItem(id, item);
        if (menuItem == null) return ResponseEntity.internalServerError().body(null);
        return ResponseEntity.ok().body(menuItem);
    }

}
