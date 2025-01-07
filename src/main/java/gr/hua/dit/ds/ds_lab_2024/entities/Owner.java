package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Owner extends User {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Property> properties = new HashSet<>();

    public Owner() {
    }

    public Owner(String username, String email, String password) {
        super(username, email, password);
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    // Additional owner-specific methods
    public void insertProperty(Property property) {
        property.setOwner(this);
        this.properties.add(property);
    }
}
