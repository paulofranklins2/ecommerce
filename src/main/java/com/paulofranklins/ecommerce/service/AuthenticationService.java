package com.paulofranklins.ecommerce.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.paulofranklins.ecommerce.dto.user.LoginUserDto;
import com.paulofranklins.ecommerce.dto.user.RegisterUserDto;
import com.paulofranklins.ecommerce.model.User;
import com.paulofranklins.ecommerce.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User registerUser(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        return userRepository.save(user);
    }

    public User authenticateUser(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
        return userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User register(Payload payload) {
        User user = new User();
        user.setFirstName(payload.get("given_name").toString());
        user.setLastName(payload.get("family_name").toString());
        user.setEmail(payload.getEmail());
        user.setPassword(passwordEncoder.encode(generateRandomPassword()));

        return userRepository.save(user);
    }

    private static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

}
