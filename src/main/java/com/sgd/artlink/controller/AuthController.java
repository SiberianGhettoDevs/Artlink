package com.sgd.artlink.controller;

import com.sgd.artlink.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/auth")
public interface AuthController {

    @PostMapping("/signup")
    ResponseEntity<Void> signup(@RequestBody UserDto userDto);

    @GetMapping("/users")
    ResponseEntity<List<UserDto>> users();

}
