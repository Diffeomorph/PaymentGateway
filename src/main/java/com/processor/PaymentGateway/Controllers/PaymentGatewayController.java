package com.processor.PaymentGateway.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.processor.PaymentGateway.Services.Payment;
import com.processor.PaymentGateway.Services.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.TreeMap;

/**
 * The PaymentGatewayController provides the endpoints for the merchant to submit payments and retrieve past attempted
 * transactions.
 */
@RestController
public class PaymentGatewayController {

    @Autowired
    private Payments payments;

    // get all past payments made through gateway
    @GetMapping("/payments")
    TreeMap<Integer,Payment> printPayments() throws JsonProcessingException {
        TreeMap<Integer,Payment> currentPayments = payments.printPayments();
        return currentPayments;
    }

    // get particular payment by payment id
    @GetMapping("/payments/{id}")
    Payment printPayments(@PathVariable int id) throws CloneNotSupportedException {
        return payments.getPaymentByIdMasked(id);
    }

    // process payment and receive true/false confirmation
    @PostMapping("/payments")
    boolean submitPayment(@RequestParam String cardNumber, @RequestParam String expiryDate, @RequestParam double amount, @RequestParam String currency, @RequestParam Integer cvv, @RequestParam boolean success){
        Payment newPayment = new Payment(cardNumber, expiryDate, amount, currency, cvv, success);

        if (newPayment.performValidation() == false){
            return false;
        }

        String uri = "http://localhost:8080/transactions";
        RestTemplate restTemplate = new RestTemplate();
        boolean result = restTemplate.postForObject(uri,null,boolean.class);
        payments.setCur_id(payments.getCur_id() + 1);
        if (result == true) {
            newPayment.setSuccess(true);
        }
        payments.getPaymentsList().put(payments.getCur_id(), newPayment);
        return result;
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

