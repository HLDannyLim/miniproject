package SSLWD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import SSLWD.model.ContactForm;
import SSLWD.model.PurchaseResponse;
import SSLWD.repo.SslwdRepo;

@Controller
@RequestMapping(path ="/api")
// @CrossOrigin("http://sslwd.s3-website-us-east-1.amazonaws.com")
@CrossOrigin("http://localhost:4200")
public class ContactUsController {
    @Autowired
    SslwdRepo sslwdRepo;

    
    @PostMapping("/contactform")
    public ResponseEntity<PurchaseResponse> saveContactForm(@RequestBody ContactForm cf){
        PurchaseResponse queryResponse = sslwdRepo.insertForm(cf);
        if (queryResponse.getOrderTrackingNumber() == "") {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(queryResponse, HttpStatus.OK);
        }
    }
}
