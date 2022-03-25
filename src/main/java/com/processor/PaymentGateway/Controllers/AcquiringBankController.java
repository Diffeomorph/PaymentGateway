package com.processor.PaymentGateway.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The AcquiringBankController provides the framework to receive transactions from the payment gateway and return whether
 * the payment has been successful or not.
 */
@RestController
public class AcquiringBankController {

    @GetMapping("/transactions")
    void printTransactions() {

    }

    @GetMapping("/transactions/{id}")
    void getTransactionById(){
        //code
    }

    // this receives the transaction from the gateway, and for testing purposes always returns true
    @PostMapping("/transactions")
    boolean doTransaction(){
        return true;
    }

    @DeleteMapping("/transactions/{id}")
    void deleteTransaction(@PathVariable int id, @RequestParam String lll){
        // code
    }

    @PutMapping("/transactions/{id}")
    void updateTransaction(){
        //code
    }

}

