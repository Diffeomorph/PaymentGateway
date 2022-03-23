package com.processor.PaymentGateway.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.processor.PaymentGateway.Services.Payment;
import com.processor.PaymentGateway.Services.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentGatewayController {

    @Autowired
    private Payments payments;

    // get all past payments made through gateway
    @GetMapping("/payments")
    String printPayments() throws JsonProcessingException {
        String currentPayments = payments.printPayments();
        return currentPayments;
    }

    // get particular payment by payment id
    @GetMapping("/payments/{id}")
    Payment printPayments(@PathVariable int id) {
        return payments.getPaymentById(id);
    }

    // process payment and receive true/false confirmation
    @PostMapping("/payments")
    boolean submitPayment(@RequestParam int cardNumber, @RequestParam String expiryDate, @RequestParam double amount, @RequestParam String currency, @RequestParam Integer cvv){
        Payment newPayment = new Payment(cardNumber, expiryDate, amount, currency, cvv);
        // perform validation
        if (newPayment.performValidation() == false){
            return false;
        }

        String uri = "http://localhost:8080/transaction";
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.getForObject(uri, boolean.class);
        if (result == true) {
            payments.setCur_id(payments.getCur_id() + 1);
            payments.getPaymentsList().put(payments.getCur_id(), newPayment);
            return true;
        } else {
            return false;
        }
    }

    @DeleteMapping("/payments/{id}")
    void deletePayment(@PathVariable int id, @RequestParam String lll){
        // code
    }

    @PutMapping("/payments/{id}")
    void updatePayment(){
        //code
    }

}

