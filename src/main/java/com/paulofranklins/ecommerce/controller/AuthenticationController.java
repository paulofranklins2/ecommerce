package com.paulofranklins.ecommerce.controller;

import com.paulofranklins.ecommerce.common.LoginResponse;
import com.paulofranklins.ecommerce.dto.user.LoginUserDto;
import com.paulofranklins.ecommerce.dto.user.RegisterUserDto;
import com.paulofranklins.ecommerce.model.User;
import com.paulofranklins.ecommerce.service.AuthenticationService;
import com.paulofranklins.ecommerce.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth/")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.registerUser(registerUserDto));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User user = authenticationService.authenticateUser(loginUserDto);
        String jwtToken = jwtService.generateToken(null, user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(loginResponse);
    }
}
