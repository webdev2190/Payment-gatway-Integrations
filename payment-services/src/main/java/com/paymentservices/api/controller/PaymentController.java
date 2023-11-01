package com.paymentservices.api.controller;

import com.paymentservices.api.entiry.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Value("${stripe.apiKey}")
    private String stripeApiKey;
    //http://localhost:8080/payments
    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody Order order) {
        try {
            Stripe.apiKey = stripeApiKey;
            PaymentIntentCreateParams createParams = new
                    PaymentIntentCreateParams.Builder()
                    .setCurrency("usd")
                    .setAmount(order.getAmount())
                    .setDescription(order.getDescription())
                    .build();
            PaymentIntent paymentIntent =
                    PaymentIntent.create(createParams);
            return ResponseEntity.ok(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            return
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()
                    );
        }
    }
}

