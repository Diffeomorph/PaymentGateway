package com.processor.PaymentGateway.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Service
public final class Payments {
    TreeMap<Integer, Payment> paymentsList;
    int cur_id;

    public Payments(){
        this.cur_id = 0;
        this.paymentsList = new TreeMap<>();
    }

    public TreeMap<Integer, Payment> printPayments() throws JsonProcessingException {
        return paymentsList;
    }

    public TreeMap<Integer, Payment> getPaymentsList() {
        return paymentsList;
    }

    public TreeMap<Integer, Payment> getPaymentsListMasked() {
        TreeMap<Integer, Payment> maskedPaymentsList = new TreeMap<Integer, Payment>();
        return maskedPaymentsList;
    }

    public Payment getPaymentById(int id){
        return paymentsList.get(id);
    }

    public Payment getPaymentByIdMasked(int id) throws CloneNotSupportedException {
        Payment requestedPayment = (Payment) paymentsList.get(id).clone(); // cloned as don't want to alter the actual payment
        requestedPayment.maskCardNumber();
        return requestedPayment;
    }

    public int getCur_id(){
        return cur_id;
    }

    public void setCur_id(int cur_id) {
        this.cur_id = cur_id;
    }

}
