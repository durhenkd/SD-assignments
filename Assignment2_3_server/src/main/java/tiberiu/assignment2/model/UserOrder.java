package tiberiu.assignment2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserOrder {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //no setter

    @OneToMany
    private List<OrderItem> items;

    @ToString.Include
    @Setter
    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne
    @ToString.Include
    private Restaurant restaurant;

    public UserOrder(List<OrderItem> items, OrderStatus status, Restaurant restaurant) {
        this.items = items;
        this.status = status;
        this.restaurant = restaurant;
    }
}
