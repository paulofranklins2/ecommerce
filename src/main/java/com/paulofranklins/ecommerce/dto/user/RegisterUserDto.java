package com.paulofranklins.ecommerce.dto.user;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
