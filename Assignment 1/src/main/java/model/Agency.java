package model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)  private String name;
    @Column(unique = true, nullable = false) private String email;
    @Column(nullable = false) private String passwordHash;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "agency_id", referencedColumnName = "id")
    private List<VacationPackage> packages;

    @Column private String description;

    public String getPasswordHash() {
        return passwordHash;
    }
    public int getId() {
        return id;
    }

    public Agency() {

    }

    public Agency(String name, String email, String passwordHash, String description, List<VacationPackage> packages) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.description = description;
        this.packages = packages;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}
    public List<VacationPackage> getPackages() {return packages;}
    public void setPackages(List<VacationPackage> packages) {this.packages = packages;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getEmail() {return email;}

    @Override
    public String toString() {
        return "Agency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", packages=" + packages +
                ", description='" + description + '\'' +
                '}';
    }
}
