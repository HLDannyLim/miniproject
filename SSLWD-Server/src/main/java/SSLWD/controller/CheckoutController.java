package SSLWD.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stripe.exception.StripeException;

import SSLWD.model.OrderDetails;
import SSLWD.model.PurchaseResponse;
import SSLWD.repo.SslwdRepo;
import SSLWD.service.CheckoutService;

@Controller
@RequestMapping(path ="/api")
// @CrossOrigin("http://sslwd.s3-website-us-east-1.amazonaws.com")
@CrossOrigin("http://localhost:4200")
public class CheckoutController {
    @Autowired
    SslwdRepo sslwdRepo;

    @Autowired
    CheckoutService checkoutSvc;


    @PostMapping("/orderdetails")
    public ResponseEntity<PurchaseResponse> placeOrder(@RequestBody OrderDetails od){
        PurchaseResponse purchaseResponse = checkoutSvc.placeOrder(od);
        if (purchaseResponse.getOrderTrackingNumber() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(purchaseResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody OrderDetails od) throws StripeException {
        // PaymentIntent paymentIntent = checkoutSvc.createPaymentIntent(paymentInfo);
        Map<String, Object> paymentIntent = checkoutSvc.createPaymentIntent(od);
        System.out.println(paymentIntent);
        // String paymentString = paymentIntent.toJson();
        // System.out.println(paymentString);

        return new ResponseEntity<>(paymentIntent, HttpStatus.OK);
    }


}
