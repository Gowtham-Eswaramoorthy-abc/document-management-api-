package com.documents.demo.config;

import com.documents.demo.entity.User;
import com.documents.demo.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdmin(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByEmail("admin@demo.com").isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@demo.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(User.Role.ADMIN);
                admin.setCreatedAt(LocalDateTime.now());

                repo.save(admin);
            }
        };
    }
}