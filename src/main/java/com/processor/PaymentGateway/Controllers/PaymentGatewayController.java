package com.processor.PaymentGateway.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.processor.PaymentGateway.Services.Pair;
import com.processor.PaymentGateway.Services.Payment;
import com.processor.PaymentGateway.Services.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.TreeMap;

/**
 * The PaymentGatewayController provides the endpoints for the merchant to submit payments and retrieve past attempted
 * transactions.
 */
@RestController
public class PaymentGatewayController {

    @Autowired
    private Payments payments;

    // Get all past payments made through gateway (masked response)
    @GetMapping("/payments")
    TreeMap<Integer, Pair<Payment, Boolean>> getPayments() throws JsonProcessingException, CloneNotSupportedException {
        TreeMap<Integer,Pair<Payment, Boolean>> currentPayments = payments.getPaymentsTreeMap();
        return currentPayments;
    }

    // Get particular payment by payment id
    @GetMapping("/payments/{id}")
    Payment getPaymentById(@PathVariable int id) throws CloneNotSupportedException {
        return payments.getPaymentById(id);
    }

    // Process payment and receive true/false confirmation
    @PostMapping("/payments")
    boolean submitPayment(@RequestBody Payment newPayment){
        return payments.submitPayment(newPayment);
    }

    @DeleteMapping("/payments/{id}")
    void deletePayment(){
        // code
    }

    @PutMapping("/payments/{id}")
    void updatePayment(){
        //code
    }

}

