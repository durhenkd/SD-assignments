package tiberiu.assignment2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import tiberiu.assignment2.model.Restaurant;
import tiberiu.assignment2.model.UserOrder;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder,Long> {

    List<UserOrder> findAllByRestaurant(Restaurant r);

}
