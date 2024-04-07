package dev.mark.jewelsstorebackend.payments.stripe;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import com.stripe.Stripe;
// import com.stripe.exception.ApiConnectionException;
// import com.stripe.exception.AuthenticationException;
// import com.stripe.exception.CardException;
// import com.stripe.exception.InvalidRequestException;
// import com.stripe.exception.StripeException;
// import com.stripe.model.Charge;

// import jakarta.annotation.PostConstruct;

// @Service
// public class StripeService {

//     @Value("${STRIPE_SECRET_KEY}")
//     private String secretKey;
    
//     @PostConstruct
//     public void init() {
//         Stripe.apiKey = secretKey;
//     }
//     public Charge charge(ChargeRequest chargeRequest) 
//       throws StripeException, AuthenticationException, InvalidRequestException,
//         ApiConnectionException, CardException, ApiConnectionException {
//         Map<String, Object> chargeParams = new HashMap<>();
//         chargeParams.put("amount", chargeRequest.getAmount());
//         chargeParams.put("currency", chargeRequest.getCurrency());
//         chargeParams.put("description", chargeRequest.getDescription());
//         chargeParams.put("source", chargeRequest.getStripeToken());
//         return Charge.create(chargeParams);
//     }
// }