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

    // Get all past payments made through gateway
    @GetMapping("/payments")
    TreeMap<Integer,Payment> printPayments() throws JsonProcessingException {
        TreeMap<Integer,Payment> currentPayments = payments.printPayments();
        return currentPayments;
    }

    // Get particular payment by payment id
    @GetMapping("/payments/{id}")
    Payment printPayments(@PathVariable int id) throws CloneNotSupportedException {
        return payments.getPaymentByIdMasked(id);
    }

    // Process payment and receive true/false confirmation
    @PostMapping("/payments")
    boolean submitPayment(@RequestBody Payment newPayment){
        return payments.submitPayment(newPayment);
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

