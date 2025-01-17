package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Request;
import gr.hua.dit.ds.ds_lab_2024.service.PropertyService;
import gr.hua.dit.ds.ds_lab_2024.service.RequestService;
import gr.hua.dit.ds.ds_lab_2024.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final PropertyService propertyService;

    @Autowired
    public RequestController(RequestService requestService, UserService userService, PropertyService propertyService) {
        this.requestService = requestService;
        this.userService = userService;
        this.propertyService = propertyService;
    }

    @GetMapping
    public String showRequests(Model model) {
        List<Request> requests = requestService.getRequests();
        model.addAttribute("requests", requests);
        return "request/requests";
    }

    // Allow both ADMIN and verified TENANT to access this endpoint
    @Secured({"ROLE_ADMIN", "ROLE_TENANT"})
    @GetMapping("/new")
    public String addRequest(Model model) {
        // Fetch the logged-in user
        var loggedInUser = userService.getLoggedInUserByEmail(); // Assuming this method exists

        // If the user is a tenant, ensure they are verified
        boolean isTenant = loggedInUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_TENANT"));
        Integer verifiedStatus = loggedInUser.getVerified();

        if (isTenant && (verifiedStatus == null || verifiedStatus != 2)) {
            model.addAttribute("errorMessage", "You must be verified to add a request.");
            return "error/error_unverified"; // Redirect to an error page or display an error message
        }

        // Prepare the request form
        Request request = new Request();
        model.addAttribute("request", request);

        if (isTenant) {
            // Pass only the logged-in tenant to the model
            model.addAttribute("tenants", List.of(loggedInUser));
        } else {
            // For admins, pass all tenants
            model.addAttribute("tenants", userService.getUsers());
        }

        model.addAttribute("properties", propertyService.getProperties());
        return "request/request";
    }

    // Allow both ADMIN and verified TENANT to save a new request
    @Secured({"ROLE_ADMIN", "ROLE_TENANT"})
    @PostMapping("/new")
    public String saveRequest(@Valid @ModelAttribute("request") Request request,
                              BindingResult theBindingResult, Model model) {
        // Fetch the logged-in user
        var loggedInUser = userService.getLoggedInUserByEmail();

        // If the user is a tenant, ensure they are verified
        boolean isTenant = loggedInUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_TENANT"));
        Integer verifiedStatus = loggedInUser.getVerified();

        if (isTenant && (verifiedStatus == null || verifiedStatus != 2)) {
            model.addAttribute("errorMessage", "You must be verified to add a request.");
            return "error/error_unverified";
        }

        if (theBindingResult.hasErrors()) {
            // Reload the form with validation errors
            if (isTenant) {
                model.addAttribute("tenants", List.of(loggedInUser));
            } else {
                model.addAttribute("tenants", userService.getUsers());
            }
            model.addAttribute("properties", propertyService.getProperties());
            System.out.println("Error in form submission");
            return "request/request";
        } else {
            // Save the request and redirect
            requestService.createRequest(request);
            model.addAttribute("requests", requestService.getRequests());
            model.addAttribute("successMessage", "Request added successfully!");
            return "redirect:/request"; // Redirect to the list of requests
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteRequest(@PathVariable Integer id, Model model) {
        requestService.deleteRequest(id);
        model.addAttribute("msg", "Request deleted successfully!");
        return "redirect:/request";
    }
}
