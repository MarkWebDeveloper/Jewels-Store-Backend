package dev.mark.jewelsstorebackend.payments.stripe;

// import java.util.Map;
// import java.util.Optional;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.google.gson.JsonSyntaxException;
// import com.stripe.exception.SignatureVerificationException;
// import com.stripe.model.Event;
// import com.stripe.model.EventDataObjectDeserializer;
// import com.stripe.model.PaymentIntent;
// import com.stripe.model.PaymentMethod;
// import com.stripe.model.StripeObject;
// import com.stripe.net.ApiResource;
// import com.stripe.net.HttpHeaders;
// import com.stripe.net.Webhook;

// import lombok.AllArgsConstructor;

// @RestController
// @AllArgsConstructor
// @RequestMapping(path = "${api-endpoint}/stripe")
// public class WebhookController {

//     @PostMapping(path = "/webhook")
//     public ResponseEntity<String> create(@RequestBody String payload, @RequestHeader HttpHeaders header) {

//         Event event = null;

//         try {
//             event = ApiResource.GSON.fromJson(payload, Event.class);
//         } catch (JsonSyntaxException e) {
//             // Invalid payload
//             System.out.println("⚠️  Webhook error while parsing basic request.");
//             return ResponseEntity.status(400).body("");
//         }
//         // Optional<String> sigHeader = header.firstValue("Stripe-Signature");
//         // if (endpointSecret != null && sigHeader != null) {
//         //     // Only verify the event if you have an endpoint secret defined.
//         //     // Otherwise use the basic event deserialized with GSON.
//         //     try {
//         //         event = Webhook.constructEvent(
//         //                 payload, sigHeader, endpointSecret);
//         //     } catch (SignatureVerificationException e) {
//         //         // Invalid signature
//         //         System.out.println("⚠️  Webhook error while validating signature.");
//         //         return ResponseEntity.status(400).body("");
//         //     }
//         // }
//         // Deserialize the nested object inside the event
//         EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
//         StripeObject stripeObject = null;
//         if (dataObjectDeserializer.getObject().isPresent()) {
//             stripeObject = dataObjectDeserializer.getObject().get();
//         } else {
//             // Deserialization failed, probably due to an API version mismatch.
//             // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
//             // instructions on how to handle this case, or return an error here.
//         }
//         // Handle the event
//         switch (event.getType()) {
//             case "payment_intent.succeeded":
//                 PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
//                 System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
//                 // Then define and call a method to handle the successful payment intent.
//                 // handlePaymentIntentSucceeded(paymentIntent);
//                 break;
//             case "payment_method.attached":
//                 PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
//                 // Then define and call a method to handle the successful attachment of a
//                 // PaymentMethod.
//                 // handlePaymentMethodAttached(paymentMethod);
//                 break;
//             default:
//                 System.out.println("Unhandled event type: " + event.getType());
//                 break;
//         }
//         return ResponseEntity.status(200).body("");
//     }
// }
