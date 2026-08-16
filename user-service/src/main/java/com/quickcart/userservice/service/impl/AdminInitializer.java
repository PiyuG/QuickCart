package com.quickcart.userservice.service.impl;

import com.quickcart.userservice.entity.User;
import com.quickcart.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {
    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            if (!repo.existsByEmail("admin@quickcart.com")) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@quickcart.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRoles("ROLE_ADMIN");
                 System.out.println("Admin Creted");
                repo.save(admin);
            }
        };
    }
}
