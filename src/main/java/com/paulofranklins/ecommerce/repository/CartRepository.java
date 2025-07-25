package com.paulofranklins.ecommerce.repository;

import com.paulofranklins.ecommerce.model.Cart;
import com.paulofranklins.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedAt(User user);
}
