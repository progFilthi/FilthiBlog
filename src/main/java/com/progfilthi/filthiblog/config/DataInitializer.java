package com.progfilthi.filthiblog.config;

import com.progfilthi.filthiblog.enums.Roles;
import com.progfilthi.filthiblog.models.User;
import com.progfilthi.filthiblog.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) throws Exception {
        createAdminUser();
    }

    private void createAdminUser(){

        if(userRepository.existsByEmail(adminProperties.email())){
            log.info("Admin user already exists. Skipping creation of admin user");
            return;
        }

        User admin = new User();
        admin.setUsername(adminProperties.username());
        admin.setEmail(adminProperties.email());
        admin.setPassword(passwordEncoder.encode(adminProperties.password()));
        admin.setRole(Roles.ADMIN);

        userRepository.save(admin);
        log.info("Admin user created successfully!");
        log.info("Email: {}", adminProperties.email());
        log.info("Username: {}", adminProperties.username());
    }
}
