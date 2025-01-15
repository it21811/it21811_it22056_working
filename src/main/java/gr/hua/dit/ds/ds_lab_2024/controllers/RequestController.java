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
    public RequestController(RequestService requestService,UserService userService,PropertyService propertyService) {
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



    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String addRequest(Model model) {
        Request request = new Request();
        model.addAttribute("request", request);
        model.addAttribute("tenants", userService.getUsers()); // Assuming a method to fetch tenants
        model.addAttribute("properties", propertyService.getProperties());
        return "request/request";
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public String saveRequest(@Valid @ModelAttribute("request") Request request,
                              BindingResult theBindingResult, Model model) {
        if (theBindingResult.hasErrors()) {
            // If validation errors occur, reload the form with error messages
            model.addAttribute("tenants", userService.getUsers());
            model.addAttribute("properties", propertyService.getProperties());
            System.out.println("Error in form submission");
            return "request/request";
        } else {
            // Save the request object to the database
            requestService.createRequest(request);
            model.addAttribute("requests", requestService.getRequests());
            model.addAttribute("successMessage", "Request added successfully!");
            return "request/requests"; // Redirect to the list of requests after saving
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
