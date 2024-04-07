package dev.mark.jewelsstorebackend.config.payments.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class StripeConfiguration {
    @Value(value = "${stripe-secret-key}")
    private String secretKey;
}
