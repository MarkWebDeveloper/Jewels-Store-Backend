package dev.mark.jewelsstorebackend.stripe;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String clientSecret;

}
