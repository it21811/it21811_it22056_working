package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String address;

    @Column
    private String municipality;

    @Column
    private Integer floor;

    @Column
    private String sizeCategory;

    @Column
    private Integer rooms;

    @Column
    private Integer price;

    @Column
    private Integer verification; // 1: verified, 2: not verified

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    public Property() {
    }

    public Property(String address, String municipality, Integer floor, String sizeCategory,
                    Integer rooms, Integer price, Integer verification) {
        this.address = address;
        this.municipality = municipality;
        this.floor = floor;
        this.sizeCategory = sizeCategory;
        this.rooms = rooms;
        this.price = price;
        this.verification = verification;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getSizeCategory() {
        return sizeCategory;
    }

    public void setSizeCategory(String sizeCategory) {
        this.sizeCategory = sizeCategory;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getVerification() {
        return verification;
    }

    public void setVerification(Integer verification) {
        this.verification = verification;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", municipality='" + municipality + '\'' +
                ", floor=" + floor +
                ", sizeCategory='" + sizeCategory + '\'' +
                ", rooms=" + rooms +
                ", price=" + price +
                ", verification=" + verification +
                '}';
    }
}
