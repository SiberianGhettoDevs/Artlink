package com.sgd.artlink.controller;

import com.sgd.artlink.dto.UserDto;
import com.sgd.artlink.exception.ClientSideException;
import com.sgd.artlink.model.User;
import com.sgd.artlink.security.jwt.JwtTokenProvider;
import com.sgd.artlink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signin(@RequestBody UserDto userDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            User user = userService.findByEmail(userDto.getEmail());

            Map<String, String> response = createAuthResponse(user);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new ClientSideException("Invalid email or password", e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody UserDto userDto) {
        User user = User.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .build();

        userService.register(user);

        User registeredUser = userService.findByEmail(userDto.getEmail());
        Map<String, String> response = createAuthResponse(registeredUser);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> users() {
        List<UserDto> users = userService.getAll()
                                        .stream()
                                        .map(u -> UserDto.builder()
                                                            .id(u.getId())
                                                            .username(u.getUsername())
                                                            .email(u.getEmail())
                                                            .password(u.getPassword())
                                                            .role(u.getRole().getName().name())
                                                            .build()
                                        ).collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    private Map<String, String> createAuthResponse(User user) {
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        return Map.of(
                "email", user.getEmail(),
                "token", token
        );
    }
}
