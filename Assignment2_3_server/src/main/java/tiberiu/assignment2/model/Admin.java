package tiberiu.assignment2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Admin {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //no setter

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String email;
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String passHash;

    @Setter
    @OneToOne
    @JoinColumn(name = "mng_restaurant_id", referencedColumnName = "id")
    private Restaurant managedRestaurant;

    public Admin(String email, String passHash, Restaurant managedRestaurants) {
        this.email = email;
        this.passHash = passHash;
        this.managedRestaurant = managedRestaurants;
    }
}