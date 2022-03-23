package com.processor.PaymentGateway.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AcquiringBankController {

    @Autowired
    //private Payments payments;

    @GetMapping("/payments")
    void printPayments() {

    }

    @GetMapping("/payments/{id}")
    void get(){
        //code
    }

    @PostMapping("/transaction")
    boolean doTransaction(){
        return true;
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

