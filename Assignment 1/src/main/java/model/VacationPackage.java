package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class VacationPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private double price;
    @Column(nullable = false) private Date periodStart;
    @Column(nullable = false) private Date periodEnd;
    @Column(nullable = false) private String details;
    @Column(nullable = false) private int maxBooks;
    @Column(nullable = false) private String photo_url;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "agency_id", referencedColumnName = "id")
//    private Agency agency;

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "bookings")
    private List<User> bookedUsers;

    //    @ManyToMany(mappedBy = "bookings")
//    private List<User> users;

    public VacationPackage() {
    }

    public VacationPackage(String name,
                           double price,
                           Date periodStart,
                           Date periodEnd,
                           String details,
                           int maxBooks,
                           String photo_url,
                           List<User> bookedUsers,
                           Destination destination
//                           Agency agency
    ) {
        this.name = name;
        this.price = price;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.details = details;
        this.maxBooks = maxBooks;
        this.photo_url = photo_url;
        this.bookedUsers = bookedUsers;
        this.destination = destination;
//        this.agency = agency;
    }

    public int getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public Date getPeriodStart() {return periodStart;}
    public void setPeriodStart(Date periodStart) {this.periodStart = periodStart;}
    public Date getPeriodEnd() {return periodEnd;}
    public void setPeriodEnd(Date periodEnd) {this.periodEnd = periodEnd;}
    public String getDetails() {return details;}
    public void setDetails(String details) {this.details = details;}
    public int getMaxBooks() {return maxBooks;}
    public void setMaxBooks(int maxBooks) {this.maxBooks = maxBooks;}
    public String getPhoto_url() {return photo_url;}
    public void setPhoto_url(String photo_url) {this.photo_url = photo_url;}
    public List<User> getBookedUsers() {return bookedUsers;}
    public void setBookedUsers(List<User> bookedUsers) {this.bookedUsers = bookedUsers;}
    public Destination getDestination() {return destination;}
    public void setDestination(Destination destination) {this.destination = destination;}
//    public Agency getAgency() {return agency;}
//    public void setAgency(Agency agency) {this.agency = agency;}

    @Override
    public String toString() {
        return "VacationPackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", periodStart=" + periodStart +
                ", periodEnd=" + periodEnd +
                ", details='" + details + '\'' +
                ", maxBooks=" + maxBooks +
                ", photo_url='" + photo_url + '\'' +
                '}';
    }
}
