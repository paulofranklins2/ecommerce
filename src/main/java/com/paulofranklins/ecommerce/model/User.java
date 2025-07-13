package com.paulofranklins.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private @NotBlank Integer id;
    private @NotBlank String firstName;
    @Column(unique = true)
    private @NotBlank String email;
    private @NotBlank String lastName;
    private @NotBlank String password;
    private @NotBlank String imageUrl;
    private @NotBlank String phone;
    private @NotBlank String address;
    private @NotBlank String city;
    private @NotBlank String state;
    private @NotBlank String country;
    private @NotBlank String zip;
    private @CreationTimestamp Date createdAt;
    private @UpdateTimestamp Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
        return true;

    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return true;

    }
}
