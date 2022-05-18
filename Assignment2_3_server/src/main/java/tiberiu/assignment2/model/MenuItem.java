package tiberiu.assignment2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class MenuItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //no setter

    @Setter
    @Column(nullable = false)
    private String name;
    @Setter
    @Column(nullable = false)
    private String description;
    @Setter
    @Column(nullable = false)
    private String imageUrl;
    @Setter
    @Column(nullable = false)
    private FoodCategory foodCategory;
    @Setter
    @Column(nullable = false)
    private double price;

    public MenuItem(String name, String description, String imageUrl, FoodCategory foodCategory, double price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.foodCategory = foodCategory;
        this.price = price;
    }
}
