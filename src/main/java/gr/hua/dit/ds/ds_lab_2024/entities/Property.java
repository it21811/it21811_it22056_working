package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "propertyid")
    private int propertyid;

    @Column(name = "address")
    private String address;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "price")
    private int price;

    @Column(name = "verified")
    private int verified;

    @ManyToOne
    @JoinColumn(name = "user_id") // Changed from owner_id to user_id
    private User user; // Changed from owner to user

    public Property(int propertyid, String address, String municipality, int price, int verified, User user) { // Changed from owner to user
        this.propertyid = propertyid;
        this.address = address;
        this.municipality = municipality;
        this.price = price;
        this.verified = verified;
        this.user = user; // Changed from owner to user
    }

    public Property() {
    }

    // Getters and Setters
    public int getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(int propertyid) {
        this.propertyid = propertyid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public User getUser() { // Changed from getOwner to getUser
        return user; // Changed from owner to user
    }

    public void setUser(User user) { // Changed from setOwner to setUser
        this.user = user; // Changed from owner to user
    }
    @Override
    public String toString() {
        return "Property{" +
                "id=" + propertyid +
                ", address='" + address + '\'' +
                ", municipality=" + municipality +
                ", price=" + price +
                ", verified=" + verified +
                ", user=" + user +
                '}';
    }
}

