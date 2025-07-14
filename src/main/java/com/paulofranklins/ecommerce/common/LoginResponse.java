package com.paulofranklins.ecommerce.common;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long expiresIn;
}
