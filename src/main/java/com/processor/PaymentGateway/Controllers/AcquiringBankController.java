package com.processor.PaymentGateway.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AcquiringBankController {

    @GetMapping("/transactions")
    void printTransactions() {

    }

    @GetMapping("/transactions/{id}")
    void getTransactionById(){
        //code
    }

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

