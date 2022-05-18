package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String countryName;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "destination")
    private List<VacationPackage> packages;

    public Destination() {
    }

    public Destination(String name, String countryName, List<VacationPackage> packages) {
        this.name = name;
        this.countryName = countryName;
        this.packages = packages == null ? new ArrayList<>() : packages;
    }

    public int getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getCountryName() {return countryName;}
    public void setCountryName(String countryName) {this.countryName = countryName;}
    public List<VacationPackage> getPackages() {return packages;}

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;

        if (obj instanceof Destination d)
        {
            return (d.id == this.id) && (d.countryName.equals(this.countryName)) && (d.name.equals(this.name));
        }
        else
            return false;
    }

    @Override
    public String toString() {
        return name + ", " + countryName;
    }
}
