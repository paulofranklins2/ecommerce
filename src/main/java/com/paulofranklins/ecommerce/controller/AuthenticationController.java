package com.paulofranklins.ecommerce.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.paulofranklins.ecommerce.common.LoginResponse;
import com.paulofranklins.ecommerce.dto.GoogleOAuthRequest;
import com.paulofranklins.ecommerce.dto.user.LoginUserDto;
import com.paulofranklins.ecommerce.dto.user.RegisterUserDto;
import com.paulofranklins.ecommerce.model.User;
import com.paulofranklins.ecommerce.service.AuthenticationService;
import com.paulofranklins.ecommerce.service.GoogleOAuthService;
import com.paulofranklins.ecommerce.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final GoogleOAuthService googleOAuthService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, GoogleOAuthService googleOAuthService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.googleOAuthService = googleOAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.registerUser(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User user = authenticationService.authenticateUser(loginUserDto);
        String jwtToken = jwtService.generateToken(null, user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/oauth2/callback/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleOAuthRequest googleOAuthRequest) {
        GoogleTokenResponse googleTokenResponse = googleOAuthService.exchangeCodeForTokens(googleOAuthRequest.getCode());

        String idToken = googleTokenResponse.getIdToken();
        Payload payload = googleOAuthService.verifyToken(idToken);

        if (!payload.getEmailVerified()) {
            throw new RuntimeException("Google account email not verified");
        }
        User user;

        try {
            user = authenticationService.findByEmail(payload.getEmail());
        } catch (Exception e) {
            user = authenticationService.register(payload);
        }

        String jwtToken = jwtService.generateToken(null, user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(loginResponse);
    }
}
