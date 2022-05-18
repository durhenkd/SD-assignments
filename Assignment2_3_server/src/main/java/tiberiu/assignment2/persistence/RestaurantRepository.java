package tiberiu.assignment2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import tiberiu.assignment2.model.MenuItem;
import tiberiu.assignment2.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {



}
