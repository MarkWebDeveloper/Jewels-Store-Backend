package dev.mark.jewelsstorebackend.stripe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "${api-endpoint}/payments")
public class PaymentController {

    PaymentService service;

    @PostMapping(path = "/create-payment-intent")
    public ResponseEntity<PaymentResponse> create(@RequestBody Payment payment) throws StripeException {

        PaymentResponse response = service.createPaymentIntent(payment);

        return ResponseEntity.status(201).body(response);
    }
}
