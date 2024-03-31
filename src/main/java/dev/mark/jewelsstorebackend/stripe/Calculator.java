package dev.mark.jewelsstorebackend.stripe;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Calculator {

    static Long calculateOrderAmount(PaymentItem[] items) {

    Long total = 0L; 

    for (PaymentItem object : items) {
      total += object.getPrice();
    }
    return total;
  }
}
