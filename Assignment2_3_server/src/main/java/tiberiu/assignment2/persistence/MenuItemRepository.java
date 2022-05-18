package tiberiu.assignment2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import tiberiu.assignment2.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {



}
