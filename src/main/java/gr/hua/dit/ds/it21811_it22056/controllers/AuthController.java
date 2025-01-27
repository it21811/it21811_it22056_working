package gr.hua.dit.ds.it21811_it22056.controllers;

import gr.hua.dit.ds.it21811_it22056.entities.Role;
import gr.hua.dit.ds.it21811_it22056.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {


    RoleRepository roleRepository;

    public AuthController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void setup() {
      Role role_user = new Role("ROLE_USER");
      Role role_admin = new Role("ROLE_ADMIN");

      roleRepository.updateOrInsert(role_user);
      roleRepository.updateOrInsert(role_admin);
      Role role_tenant = new Role("ROLE_TENANT");
      roleRepository.updateOrInsert(role_tenant);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
