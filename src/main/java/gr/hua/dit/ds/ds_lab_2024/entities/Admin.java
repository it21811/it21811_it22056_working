package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {
    }

    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    // Additional admin-specific methods
    public void createUser(User user) {
        // Logic for creating a user
    }

    public void approveTenant(Tenant tenant) {
        // Logic for approving a tenant
    }

    public void approveProperty(Property property) {
        // Logic for approving a property
    }
}
