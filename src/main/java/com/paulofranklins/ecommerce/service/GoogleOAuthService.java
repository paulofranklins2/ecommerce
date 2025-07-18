package com.paulofranklins.ecommerce.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.util.Objects.nonNull;

@Service
public class GoogleOAuthService {

    @Value("${google.client_id}")
    private String clientId;

    @Value("${google.client_secret}")
    private String clientSecret;

    @Value("${google.redirect_uri}")
    private String redirectUri;

    @SneakyThrows
    public GoogleTokenResponse exchangeCodeForTokens(String code) {
        return new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                new GsonFactory(),
                clientId,
                clientSecret,
                code,
                redirectUri
        ).execute();
    }

    @SneakyThrows
    public GoogleIdToken.Payload verifyToken(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (nonNull(idToken)) return idToken.getPayload();
        else throw new IllegalArgumentException("Invalid ID token");
    }
}
