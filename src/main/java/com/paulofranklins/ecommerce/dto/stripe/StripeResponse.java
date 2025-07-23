package com.paulofranklins.ecommerce.dto.stripe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StripeResponse {
    private String checkOutUrl;
}
