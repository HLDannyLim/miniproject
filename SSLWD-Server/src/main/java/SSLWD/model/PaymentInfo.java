package SSLWD.model;

import lombok.Data;

@Data
public class PaymentInfo {
    private int amount;
    private String currency;
    private OrderDetails orderDetails;
}
