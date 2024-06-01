package dev.mark.jewelsstorebackend.payments.stripe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "${api-endpoint}/any/payments")
public class PaymentController {

    PaymentService service;

    @PostMapping(path = "/create-payment-intent/{paymentProvider}")
    public ResponseEntity<PaymentResponse> create(@PathVariable("paymentProvider") String paymentProvider, @RequestBody PaymentRequest payment) throws StripeException {

        PaymentResponse response = service.createPaymentIntent(paymentProvider, payment);

        return ResponseEntity.status(201).body(response);
    }
}
