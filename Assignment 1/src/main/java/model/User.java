package model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)  private String firstName;
    @Column private String lastName;
    @Column(unique = true, nullable = false) private String email;
    @Column(nullable = false) private String passwordHash;
    @Column(unique = true, nullable = false) private String username;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "booking",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "package_id"))
    private List<VacationPackage> bookings;

    public List<VacationPackage> getBookings() {
        return bookings;
    }

    public void setBookings(List<VacationPackage> bookings) {
        this.bookings = bookings;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public int getId() {
        return id;
    }

    public User() {

    }

    public User(String firstName, String lastName, String email, String passwordHash, String username, List<VacationPackage> bookings) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.username = username;
        this.bookings = bookings;
    }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getUsername() {return username;}

    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
