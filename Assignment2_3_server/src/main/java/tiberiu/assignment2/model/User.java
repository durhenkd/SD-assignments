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
public class User {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //no setter

    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String username;
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String firstName;
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String lastName;
    @ToString.Include
    @Setter
    @Column(nullable = false, unique = true)
    private String email;
    @ToString.Include
    @Setter
    @Column(nullable = false)
    private String passHash;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<UserOrder> orders;

    public User(String username, String firstName, String lastName, String email, String passHash, List<UserOrder> orders) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passHash = passHash;
        this.orders = orders;
    }
}
