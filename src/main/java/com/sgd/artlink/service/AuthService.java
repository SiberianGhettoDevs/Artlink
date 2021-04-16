package com.sgd.artlink.service;

import com.sgd.artlink.exception.ValidationException;
import com.sgd.artlink.model.User;

import java.util.List;

public interface AuthService {

    void signup(User user) throws ValidationException;

    List<User> users();
}
