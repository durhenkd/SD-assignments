package tiberiu.assignment2.bussiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiberiu.assignment2.model.Restaurant;
import tiberiu.assignment2.persistence.RestaurantRepository;

import java.util.Optional;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /***
     * Attempt to get the Restaurant with the given id
     * @param id - id of the restaurant/admin
     * @return the Restaurant object with the given id if found, null otherwise
     * @see Restaurant
     */
    public Restaurant getRestaurant(Long id) {
        Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);
        if (maybeRestaurant.isEmpty()) {
            log.error("Restaurant not found");
            return null;
        }
        log.info("Restaurant retrieved");
        return maybeRestaurant.get();
    }

}
