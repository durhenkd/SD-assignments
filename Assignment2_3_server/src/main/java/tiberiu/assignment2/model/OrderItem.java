package tiberiu.assignment2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //no setter

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @Setter
    private MenuItem item;
    @Setter
    @Column(nullable = false)
    private int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getPrice() {
        return quantity * item.getPrice();
    }
}
