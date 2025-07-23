package com.paulofranklins.ecommerce.service;

import com.paulofranklins.ecommerce.dto.cart.CartDto;
import com.paulofranklins.ecommerce.dto.cart.CartItemDto;
import com.paulofranklins.ecommerce.model.Order;
import com.paulofranklins.ecommerce.model.User;
import com.paulofranklins.ecommerce.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;

    public OrderService(CartService cartService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    @Value("${stripe.secret.key}")
    private String secretKey;

    PriceData createPriceData(CartItemDto cartItemDto) {
        return PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount(
                        cartItemDto.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(100))
                                .longValue()
                )
                .setProductData(
                        PriceData.ProductData.builder()
                                .setName(cartItemDto.getProduct().getName())
                                .build()
                )
                .build();
    }

    LineItem createSessionLineItem(CartItemDto cartItemDto) {
        return LineItem.builder()
                .setPriceData(createPriceData(cartItemDto))
                .setQuantity(Long.parseLong(String.valueOf(cartItemDto.getQuantity())))
                .build();
    }

    public Session createSession() throws StripeException {
        Stripe.apiKey = secretKey;
        List<LineItem> sessionItemsList = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CartDto cartDto = cartService.cartItems(user);

        for (CartItemDto cartItemDto : cartDto.getCartItems()) {
            sessionItemsList.add(createSessionLineItem(cartItemDto));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl("http://localhost:8080/#/")
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl("http://localhost:8080/#/")
                .build();

        return Session.create(params);
    }

    public void createOrder(String sessionId, String status, String metadata) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Order order = new Order(sessionId, user, metadata, status);
        orderRepository.save(order);
    }
}