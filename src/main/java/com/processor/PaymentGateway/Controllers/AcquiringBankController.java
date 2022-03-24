package com.processor.PaymentGateway.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AcquiringBankController {

    @Autowired
    //private Payments payments;

    @GetMapping("/transactions")
    void printPayments() {

    }

    @GetMapping("/transactions/{id}")
    void get(){
        //code
    }

    @PostMapping("/transactions")
    boolean doTransaction(){
        return true;
    }

    @DeleteMapping("/transactions/{id}")
    void deletePayment(@PathVariable int id, @RequestParam String lll){
        // code
    }

    @PutMapping("/transactions/{id}")
    void updatePayment(){
        //code
    }

}

