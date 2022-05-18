package tiberiu.assignment2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Restaurant {

    @OneToMany
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    List<MenuItem> items;
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //no setter
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String name;
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String location;
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String deliveryLocations;
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String photoUrl;

    public Restaurant(String name, String location, String deliveryLocations, String photoUrl, List<MenuItem> items) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.items = items;
        this.location = location;
        this.deliveryLocations = deliveryLocations;
    }
}
