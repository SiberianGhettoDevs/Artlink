package com.sgd.artlink.init;

import com.sgd.artlink.model.Role;
import com.sgd.artlink.model.Status;
import com.sgd.artlink.model.User;
import com.sgd.artlink.repository.RoleRepository;
import com.sgd.artlink.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class InitService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {

        Role role1 = new Role();
        role1.setName(Role.Name.ADMIN);
        Role role2 = new Role();
        role2.setName(Role.Name.USER);

        roleRepository.save(role1);
        roleRepository.save(role2);


        User user1 = new User();
        user1.setUsername("User");
        user1.setEmail("user@mail.ru");
        user1.setPassword(new BCryptPasswordEncoder().encode("user123"));
        user1.setRole(role1);
        user1.setStatus(Status.ACTIVE);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("Admin");
        user2.setEmail("admin@mail.com");
        user2.setPassword(new BCryptPasswordEncoder().encode("admin123"));
        user2.setRole(role2);
        user2.setStatus(Status.ACTIVE);
        userRepository.save(user2);

    }
}
