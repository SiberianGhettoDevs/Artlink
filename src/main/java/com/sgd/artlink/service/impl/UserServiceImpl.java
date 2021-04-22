package com.sgd.artlink.service.impl;

import com.sgd.artlink.exception.ClientSideException;
import com.sgd.artlink.exception.InternalServerException;
import com.sgd.artlink.exception.ValidationException;
import com.sgd.artlink.model.Role;
import com.sgd.artlink.model.Status;
import com.sgd.artlink.model.User;
import com.sgd.artlink.repository.RoleRepository;
import com.sgd.artlink.repository.UserRepository;
import com.sgd.artlink.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sgd.artlink.model.Role.Name.USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            // todo: добавить в эксепшен поле и код ошибки по этому полю
            throw new ValidationException();
        }

        Role userRole = roleRepository.findByName(USER)
                                        .orElseThrow(() -> {
                                                log.error("IN register - role not found by name {}", USER.name());
                                                return new InternalServerException(String.format("Role %s not found", USER.name()));
                                        });

        // todo: разобраться с инжектом PasswordEncoder'а
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(userRole);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = Optional.of(userRepository.findAll())
                                    .orElseThrow(() -> {
                                        log.error("IN getAll - UserRepository#findAll() returned 'null'");
                                        return new InternalServerException("UserRepository#findAll() returned 'null'");
                                    });
        log.info("IN getAll - {} users found", users.size());
        return users;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                                    .orElseThrow(() -> {
                                        log.warn("IN findByEmail - no user found by email: {}", email);
                                        return new ClientSideException("user not found by email: " + email);
                                    });

        log.info("IN findByEmail - user: {} found by email: {}", user, email);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id)
                                    .orElseThrow(() -> {
                                        log.warn("IN findById - no user found by id: {}", id);
                                        return new ClientSideException("user not found by id: " + id);
                                    });

        log.info("IN findById - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
