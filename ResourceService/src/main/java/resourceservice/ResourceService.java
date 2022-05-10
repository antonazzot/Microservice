package resourceservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import resourceservice.model.auth.AppUser;
import resourceservice.model.auth.Role;
import resourceservice.service.authservice.AuthService;

import java.util.ArrayList;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
@EnableWebSecurity
@EnableDiscoveryClient
@EnableKafka
public class ResourceService {
    public static void main(String[] args) {
        SpringApplication.run(ResourceService.class, args);
    }

    @Bean
    CommandLineRunner run (AuthService authService) {
        return args -> {
            authService.saveRole(new Role(null, "ROLE_USER"));
            authService.saveRole(new Role(null, "ROLE_ADMIN"));

            authService.saveAppUser(new AppUser(null, "Anton", "pass", new ArrayList<>()));
            authService.saveAppUser(new AppUser(null, "Vika", "word", new ArrayList<>()));
            authService.saveAppUser(new AppUser(null, "Kolya", "kolya", new ArrayList<>()));

            authService.addRoleToUser("Anton", "ROLE_ADMIN");
            authService.addRoleToUser("Vika", "ROLE_USER");
        };
    }
}
