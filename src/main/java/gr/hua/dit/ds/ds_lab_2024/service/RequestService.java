package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.entities.Request;
import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repositories.RequestRepository;
import gr.hua.dit.ds.ds_lab_2024.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final RoleRepository roleRepository ;
    private final PropertyService propertyService;
    private final UserService userService;

    @Autowired
    public RequestService(RequestRepository requestRepository, PropertyService propertyService, UserService userService,RoleRepository roleRepository) {
        this.requestRepository = requestRepository;
        this.propertyService = propertyService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // Method to create a request, automatically setting the status to 1 (verified) by default
    public void createRequest(Request request) {
        request.setStatus(2); // Set status to 1 by default for "verified"
        requestRepository.save(request);
    }

    // Method to update the status of an existing request
    public void updateRequestStatus(int requestId, int status) {
        // Ensure the status is valid
        if (status != 1 && status != 2) {
            throw new IllegalArgumentException("Status must be 1 (verified) or 2 (not verified).");
        }

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with ID: " + requestId));

        request.setStatus(status); // Update status
        requestRepository.save(request); // Save updated request
    }

    // Fetch the property owner ID based on the request ID
    public Integer getPropertyOwnerIdByRequestId(Integer requestId) {
        return requestRepository.findOwnerIdByRequestId(requestId);
    }

    // Additional methods for managing requests can be added here, such as delete, find by tenant, etc.

    // Helper method to setup some initial data (Users, Properties, Requests)
    public void setupData() {
        setupUsers();
        createProperties();
        createExampleRequests();
    }

    private void setupUsers() {
        // Check if 'bill' exists
        if (!userService.userExists("bill@example.com")) {
            User bill = new User();
            bill.setUsername("bill");
            bill.setEmail("bill@example.com");
            bill.setPassword("bill");
            userService.saveUser(bill);
        }

        // Check if 'admin' exists
        if (!userService.userExists("admin@example.com")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword("admin");
            userService.saveUser(admin);

            // Fetch the 'ROLE_ADMIN' role and assign to admin
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_ADMIN' is not found."));
            admin.getRoles().add(adminRole);
            userService.updateUser(admin);
        }

        // Check if 'tenant' exists
        if (!userService.userExists("tenant@example.com")) {
            User tenant = new User();
            tenant.setUsername("tenant");
            tenant.setEmail("tenant@example.com");
            tenant.setPassword("tenant");
            userService.saveUser(tenant);

            // Set the 'verified' value to 2 for tenant
            tenant.setVerified(2);  // Verified value is set to 2
            // Fetch the 'ROLE_TENANT' role and assign to tenant
            Role tenantRole = roleRepository.findByName("ROLE_TENANT")
                    .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_TENANT' is not found."));
            tenant.getRoles().add(tenantRole);
            userService.updateUser(tenant);
        }
    }

    private void createProperties() {
        // Example Properties to be added
        Property property1 = new Property();
        property1.setAddress("123 Main St");
        property1.setMunicipality("Municipality 1");
        property1.setPrice(1000);
        property1.setVerified(1);
        property1.setUser( userService.getbyUsername("bill")); // Assigning bill as the owner
        propertyService.saveProperty(property1);

        Property property2 = new Property();
        property2.setAddress("456 Oak St");
        property2.setMunicipality("Municipality 2");
        property2.setPrice(1200);
        property2.setVerified(1);
        property2.setUser( userService.getbyUsername("bill")); // Assigning bill as the owner
        propertyService.saveProperty(property2);

        Property property3 = new Property();
        property3.setAddress("789 Pine St");
        property3.setMunicipality("Municipality 3");
        property3.setPrice(1500);
        property3.setVerified(2); // Unverified property
        property3.setUser( userService.getbyUsername("admin")); // Assigning admin as the owner
        propertyService.saveProperty(property3);
    }

    private void createExampleRequests() {
        User tenant = userService.getbyUsername("tenant");

        // Fetch all properties from the repository
        List<Property> allProperties = propertyService.getProperties(); // Fetch all properties

        // Check if there are at least two properties
        if (allProperties.size() < 2) {
            throw new RuntimeException("Not enough available properties to create requests.");
        }

        // Get the first two properties from the list
        Property property1 = allProperties.get(0); // First property
        Property property2 = allProperties.get(1); // Second property

        // Creating requests for the tenant on the properties
        Request request1 = new Request();
        request1.setTenant(tenant);
        request1.setProperty(property1);
        createRequest(request1);

        Request request2 = new Request();
        request2.setTenant(tenant);
        request2.setProperty(property2);
        createRequest(request2);
    }

    // Fetch all requests
    public List<Request> getRequests() {
        return requestRepository.findAll();  // Fetch all requests from the repository
    }
    public Request getRequestById(Integer id) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isEmpty()) {
            throw new RuntimeException("Request not found with id: " + id);
        }
        return request.get();
    }
    public void deleteRequest(Integer id) {
        requestRepository.deleteById(id);
    }

}
