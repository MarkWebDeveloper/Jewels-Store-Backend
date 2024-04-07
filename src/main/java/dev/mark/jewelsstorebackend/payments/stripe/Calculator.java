package dev.mark.jewelsstorebackend.payments.stripe;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Calculator {

    public static Long calculateOrderAmount(PaymentItem[] items) {

    Long total = 0L; 

    for (PaymentItem object : items) {
      total += object.getPrice();
    }
    return total;
  }
}
