package com.example.healthportal;

import com.example.healthportal.Model.Role;
import com.example.healthportal.Model.User;
import com.example.healthportal.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableScheduling
@SpringBootApplication
public class HealthPortalApplication {

    public static void main(String[] args) {

        SpringApplication.run(HealthPortalApplication.class, args);
    }
    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder encoder){
        return args -> {

            if(userRepository.findByEmail("admin@gmail.com").isEmpty()){

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Admin created");
            }
        };
    }

}
