package tiberiu.assignment2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import tiberiu.assignment2.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
