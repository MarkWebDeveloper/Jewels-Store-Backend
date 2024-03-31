// package dev.mark.jewelsstorebackend.stripe;

// import java.nio.file.Paths;

// import static spark.Spark.post;
// import static spark.Spark.staticFiles;
// import static spark.Spark.port;

// import com.google.gson.Gson;
// import com.google.gson.annotations.SerializedName;

// import com.stripe.Stripe;
// import com.stripe.model.PaymentIntent;
// import com.stripe.param.PaymentIntentCreateParams;

// import dev.mark.jewelsstorebackend.products.ProductDTO;

// public class Server {
//   private static Gson gson = new Gson();

//   static class CreatePaymentItem {
//     @SerializedName("id")
//     String id;

//     public String getId() {
//       return id;
//     }
//   }

//   static class CreatePayment {
//     @SerializedName("items")
//     CreatePaymentItem[] items;

//     public CreatePaymentItem[] getItems() {
//       return items;
//     }
//   }

//   static class CreatePaymentResponse {
//     private String clientSecret;
//     public CreatePaymentResponse(String clientSecret) {
//       this.clientSecret = clientSecret;
//     }
//   }

//   static Long calculateOrderAmount(ProductDTO[] items) {
//     Long total;
//     for (ProductDTO object : items) {
//       total += object.price;
//     }
//     // Replace this constant with a calculation of the order's amount
//     // Calculate the order total on the server to prevent
//     // people from directly manipulating the amount on the client
//     return total;
//   }


//   public static void main(String[] args) {
//     port(4242);
//     staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());

//     // This is your test secret API key.
//     Stripe.apiKey = "${STRIPE_SECRET_KEY}";

//     post("/create-payment-intent", (request, response) -> {
//       response.type("application/json");
//       CreatePayment postBody = gson.fromJson(request.body(), CreatePayment.class);

//       PaymentIntentCreateParams params =
//         PaymentIntentCreateParams.builder()
//           .setAmount(calculateOrderAmount(postBody.getItems()))
//           .setCurrency("eur")
//       // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
//           .setAutomaticPaymentMethods(
//             PaymentIntentCreateParams.AutomaticPaymentMethods
//               .builder()
//               .setEnabled(true)
//               .build()
//           )
//           .build();

//       // Create a PaymentIntent with the order amount and currency
//       PaymentIntent paymentIntent = PaymentIntent.create(params);

//       CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
//       return gson.toJson(paymentResponse);
//     });
//   }
// }