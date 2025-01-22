package gr.hua.dit.ds.ds_lab_2024.controllers;


import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.service.PropertyService;
import gr.hua.dit.ds.ds_lab_2024.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("property")
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService; // Injecting UserService

    public PropertyController(PropertyService propertyService, UserService userService) {
        this.propertyService = propertyService;
        this.userService = userService;
    }

  /*  @PostConstruct
    public void setup() {
        // Fetch the user with ID 1 from the database
        User user = (User) userService.getUser(1L);
        if (user == null) {
            throw new RuntimeException("User with ID 1 not found. Please ensure the user exists.");
        }

        // Create and save properties
        Property property1 = new Property(0, "123 Main Street", "Springfield", 250000, 1, user);
        Property property2 = new Property(0, "456 Elm Street", "Shelbyville", 300000, 0, user);
        Property property3 = new Property(0, "789 Oak Street", "Ogdenville", 200000, 1, user);

        propertyService.saveProperty(property1);
        propertyService.saveProperty(property2);
        propertyService.saveProperty(property3);

        System.out.println("Setup complete: Properties have been created and associated with user ID 1.");
    }*/
    @RequestMapping()
    public String showProperties(Model model) {
        model.addAttribute("properties", propertyService.getProperties());
        return "property/properties";
    }
    @GetMapping("/{id}")
    public String showProperty(@PathVariable Integer id, Model model){
        Property property = propertyService.getPropertyById(id);
        model.addAttribute("property", property);
        return "property/properties";
    }


    @GetMapping("/new")
    public String addProperty(Model model){
        Property property = new Property();
        model.addAttribute("property", property);
        model.addAttribute("users", userService.getUsers()); // Adding users for the dropdown
        return "property/property";

    }



    @PostMapping("/new")
    public String saveProperty(@Valid @ModelAttribute("property") Property property,BindingResult theBindingResult, Model model) {
        if (theBindingResult.hasErrors()) {
            System.out.println("error");
            return "property/property";
        } else {
            propertyService.saveProperty(property);
            model.addAttribute("properties", propertyService.getProperties());
            model.addAttribute("successMessage", "Property added successfully!");
            return "property/properties";
        }
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Property property = propertyService.getPropertyById(id);
        model.addAttribute("property", property);
        model.addAttribute("users", userService.getUsers()); // Adding users for the dropdown
        return "property/edit-property"; // Points to edit-property.html
    }


    @PostMapping("update/{id}")
    public String updateProperty(@PathVariable Integer id, @ModelAttribute("property") Property property, Model model) {
        Property existingProperty = propertyService.getPropertyById(id);

        // Update the fields of the existing property
        existingProperty.setAddress(property.getAddress());
        existingProperty.setMunicipality(property.getMunicipality());
        existingProperty.setPrice(property.getPrice());
        existingProperty.setVerified(property.getVerified());
        existingProperty.setUser(property.getUser()); // Update user association if applicable

        propertyService.updateProperty(existingProperty);

        model.addAttribute("msg", "Property updated successfully!");
        return "redirect:/property";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteProperty(@PathVariable Integer id, Model model) {
        propertyService.deleteProperty(id);
        model.addAttribute("msg", "Property deleted successfully!");
        return "redirect:/property";
    }
    @GetMapping("/filter")
    public String showFilterPage(Model model) {
        List<String> municipalities = propertyService.getDistinctMunicipalities();
        model.addAttribute("municipalities", municipalities);

        return "property/filter-properties";
    }

    @GetMapping("/filter/results")
    public String filterProperties(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String municipality,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer verified,
            Model model) {
        List<Property> filteredProperties = propertyService.filterProperties(address, municipality, minPrice, maxPrice, verified);
        model.addAttribute("properties", filteredProperties);
        return "property/properties"; // Use the existing properties listing template to show results
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/verify/{id}")
    public String verifyProperty(@PathVariable Integer id, Model model) {
        try {
            Property property = propertyService.getPropertyById(id);
            property.setVerified(1); // Set the property as verified
            propertyService.updateProperty(property); // Save the changes
            model.addAttribute("msg", "Property verified successfully!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMsg", "Error verifying property: " + e.getMessage());
        }
        return "redirect:/property";
    }


}
