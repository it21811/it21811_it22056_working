package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tenant extends User {

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Request> requests = new HashSet<>();

    public Tenant() {
    }

    public Tenant(String username, String email, String password) {
        super(username, email, password);
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    // Tenant-specific methods
    public void createRequest(Request request) {
        request.setTenant(this);
        this.requests.add(request);
    }
}
