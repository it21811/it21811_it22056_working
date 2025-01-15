package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repositories.RoleRepository;
import gr.hua.dit.ds.ds_lab_2024.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UserController {

    private UserService userService;

    private RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @PostConstruct
    public void setup() {
        // Check if 'bill' already exists
        if (!userService.userExists("bill@example.com")) {
            User bill = new User();
            bill.setUsername("bill");
            bill.setEmail("bill@example.com");
            bill.setPassword("bill");
            userService.saveUser(bill);
        }

        // Check if 'admin' already exists
        if (!userService.userExists("admin@example.com")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword("admin");
            userService.saveUser(admin);
            // Fetch the 'ROLE_ADMIN' role
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_ADMIN' is not found."));

            // Add 'ROLE_ADMIN' to the 'admin' user
            admin.getRoles().add(adminRole);

            // Update the 'admin' user with the new role
            userService.updateUser(admin);
        }
        // Check if 'tenant' user exists
        if (!userService.userExists("tenant@example.com")) {
            User tenant = new User();
            tenant.setUsername("tenant");
            tenant.setEmail("tenant@example.com");
            tenant.setPassword("tenant");
            userService.saveUser(tenant);

            // Set the 'verified' value to 2 for tenant
            tenant.setVerified(2);  // Verified value is set to 2
            // Fetch the 'ROLE_TENANT' role
            Role tenantRole = roleRepository.findByName("ROLE_TENANT")
                    .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_TENANT' is not found."));

            // Add 'ROLE_TENANT' to the 'tenant' user
            tenant.getRoles().add(tenantRole);

            // Update the 'tenant' user with the new role
            userService.updateUser(tenant);
        }
    }


    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model model){
        System.out.println("Roles: "+user.getRoles());
        Integer id = userService.saveUser(user);
        String message = "User '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "auth/user";
    }

    @PostMapping("/user/{user_id}")
    public String saveStudent(@PathVariable Long user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = (User) userService.getUser(user_id);
        the_user.setEmail(user.getEmail());
        the_user.setUsername(user.getUsername());
        userService.updateUser(the_user);
        model.addAttribute("users", userService.getUsers());
        return "auth/users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";

    }
    @GetMapping("/deleteuser/{user_id}")
    public String deleteUser(@PathVariable Long user_id, Model model) {
        userService.deleteUser(user_id);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }
    @GetMapping("/profile")
    public String getProfile(Model model) {
        // Get the logged-in user by email (fetches based on the email column)
        User loggedInUser = userService.getLoggedInUserByEmail();
        model.addAttribute("user", loggedInUser);  // Add the user to the model
        return "profile";  // Return the profile view
    }


}