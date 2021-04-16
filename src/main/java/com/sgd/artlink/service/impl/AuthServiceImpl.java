package com.sgd.artlink.service.impl;

import com.sgd.artlink.exception.ServerException;
import com.sgd.artlink.exception.ValidationException;
import com.sgd.artlink.model.Role;
import com.sgd.artlink.model.User;
import com.sgd.artlink.repository.RoleRepository;
import com.sgd.artlink.repository.UserRepository;
import com.sgd.artlink.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sgd.artlink.model.Role.Name.USER;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void signup(User user) throws ValidationException {
        if (userRepository.existsByEmail(user.getEmail())) {
            // todo: добавить в эксепшен поле и код ошибки по этому полю
            throw new ValidationException();
        }

        // todo: добавить код ошибки
        Role role = roleRepository.findByName(USER)
                                    .orElseThrow(ServerException::new);
        user.setRole(role);
        userRepository.save(user);

    }

    @Override
    public List<User> users() {
        return userRepository.findAll();
    }
}
