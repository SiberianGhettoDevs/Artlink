package com.sgd.artlink.service;

import com.sgd.artlink.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    void delete(Long id);
}
