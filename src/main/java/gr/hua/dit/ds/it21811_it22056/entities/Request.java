package gr.hua.dit.ds.it21811_it22056.entities;

import jakarta.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestid")
    private int requestid;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User tenant;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "status")
    private Integer status = 1; // Default to 1 for "verified"

    // Constructors, getters, and setters

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        if (status != 1 && status != 2) {
            throw new IllegalArgumentException("Status must be 1 (verified) or 2 (not verified).");
        }
        this.status = status;
    }

    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
