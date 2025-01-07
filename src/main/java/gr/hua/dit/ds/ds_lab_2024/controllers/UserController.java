package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Fetch all users and display in a Thymeleaf template
    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users); // Add user list to the model
        return "user_list"; // Render the user_list.html template
    }




    // Handle form submission for new user registration
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userService.createUser(user); // Save the user
        return "redirect:/users"; // Redirect to the user list page
    }

    // View user details by ID
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user); // Pass the user details to the model
        return "user_details"; // Render user_details.html template
    }

    // Handle user deletion
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id); // Delete the user
        return "redirect:/users"; // Redirect to the user list page
    }
}
