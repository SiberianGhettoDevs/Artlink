package com.sgd.artlink;

import com.sgd.artlink.model.Role;
import com.sgd.artlink.model.User;
import com.sgd.artlink.repository.RoleRepository;
import com.sgd.artlink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

import static com.sgd.artlink.model.Role.Name.ADMIN;
import static com.sgd.artlink.model.Role.Name.USER;
import static com.sgd.artlink.model.Status.ACTIVE;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class ArtlinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtlinkApplication.class, args);
    }

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void handleContextStart() {
        final String defaultPassword = passwordEncoder.encode("12345");

        Role roleAdmin = new Role();
        roleAdmin.setName(ADMIN);

        Role roleUser = new Role();
        roleUser.setName(USER);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        User user = User.builder()
                .username("user")
                .email("user@mail.ru")
                .password(defaultPassword)
                .role(roleUser)
                .status(ACTIVE)
                .build();

        User admin = User.builder()
                .username("admin")
                .email("admin@mail.ru")
                .password(defaultPassword)
                .role(roleAdmin)
                .status(ACTIVE)
                .build();

        userRepository.save(user);
        userRepository.save(admin);
    }

}
