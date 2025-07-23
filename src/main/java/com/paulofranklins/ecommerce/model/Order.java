package com.paulofranklins.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String sessionId;
    @ManyToOne
    private User user;
    private String metadata;
    @CreationTimestamp
    private Date createdAt;
    private String status;

    public Order(String sessionId, User user, String metadata, String status) {
        this.sessionId = sessionId;
        this.user = user;
        this.metadata = metadata;
        this.status = status;
        this.createdAt = new Date();
    }
}
