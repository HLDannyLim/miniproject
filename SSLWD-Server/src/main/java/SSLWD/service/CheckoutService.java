package SSLWD.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionCreateParams.PaymentSettings.SaveDefaultPaymentMethod;

import SSLWD.model.OrderDetails;
import SSLWD.model.PurchaseResponse;
import SSLWD.repo.SslwdRepo;

@Service
public class CheckoutService {

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Autowired
    SslwdRepo sslwdRepo;

    @Transactional
    public PurchaseResponse placeOrder(OrderDetails od) {
        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        od.setOrderTrackingNumber(orderTrackingNumber);
        // od.setDateCreated(new Date().toString());
        // save to the database
        sslwdRepo.insertOrderDetails(od);
        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4)

        return UUID.randomUUID().toString();
    }

    public Map<String, Object> createPaymentIntent(OrderDetails od) throws StripeException {
        Stripe.apiKey = secretKey;
        String priceId ;
        String description ;

        if (od.getService().equals("b")) {
            priceId = "price_1N97NTBXu20qYSKP5OKX7kiS";
            description = "Basic Plan";
          } else if (od.getService().equals("s")) {
            priceId = "price_1N97NdBXu20qYSKPLrs2Xquv";
            description = "Standard Plan";
          } else if (od.getService().equals("p")) {
            priceId = "price_1N97OTBXu20qYSKPE0lQASVw";
            description = "Pro Plan";
          }else{
            priceId = "";
            description = "";
          }


        CustomerCreateParams paramss = CustomerCreateParams.builder()
                .setEmail(od.getEmail())
                .setName(od.getFirstName()+ " " + od.getLastName())
                .setPhone(od.getPhone().toString())
                .setDescription(description)
                // .setShipping(
                //         CustomerCreateParams.Shipping.builder()
                //                 .setAddress(
                //                         CustomerCreateParams.Shipping.Address.builder()
                //                                 .setCity(od.getCity())
                //                                 .setCountry(od.getCountry())
                //                                 .setLine1(od.getAddress1())
                //                                 .setLine2(od.getAddress2())
                //                                 .setPostalCode(od.getZip())
                //                                 .setState(od.getState())
                //                                 .build())
                //                 .setName(od.getFirstName()+ " " + od.getLastName())
                //                 .build())
                .setAddress(
                        CustomerCreateParams.Address.builder()
                        .setCity(od.getCity())
                        .setCountry(od.getCountry())
                        .setLine1(od.getAddress1())
                        .setLine2(od.getAddress2())
                        .setPostalCode(od.getZip())
                        .setState(od.getState())
                        .build())
                .build();

        Customer customer = Customer.create(paramss);



        // Automatically save the payment method to the subscription
        // when the first payment is successful.
        SubscriptionCreateParams.PaymentSettings paymentSettings = SubscriptionCreateParams.PaymentSettings
                .builder()
                .setSaveDefaultPaymentMethod(SaveDefaultPaymentMethod.ON_SUBSCRIPTION)
                .build();

        // Create the subscription. Note we're expanding the Subscription's
        // latest invoice and that invoice's payment_intent
        // so we can pass it to the front end to confirm the payment
        SubscriptionCreateParams subCreateParams = SubscriptionCreateParams
                .builder()
                .setCustomer(customer.getId())
                .addItem(
                        SubscriptionCreateParams.Item.builder()
                                .setPrice(priceId)
                                .build())
                .setPaymentSettings(paymentSettings)
                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
                .addAllExpand(Arrays.asList("latest_invoice.payment_intent"))
                .build();

        Subscription subscription = Subscription.create(subCreateParams);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("subscriptionId", subscription.getId());
        responseData.put("clientSecret",
                subscription.getLatestInvoiceObject().getPaymentIntentObject().getClientSecret());

        return responseData;

    }

}
