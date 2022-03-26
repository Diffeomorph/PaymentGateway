package com.processor.PaymentGateway.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.TreeMap;

/**
 * The Payments class provide the structure to hold all attempted payments. It also provides functionality to retrieve
 * previous transactions and mask the card number.
 */
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

    public boolean submitPayment(Payment newPayment){
        if (newPayment.performValidation() == false){
            return false;
        }
        String uri = "http://localhost:8080/transactions";
        RestTemplate restTemplate = new RestTemplate();
        boolean result = restTemplate.postForObject(uri,null,boolean.class);
        this.setCur_id(this.getCur_id() + 1);
        if (result == true) {
            newPayment.setSuccess(true);
        }
        this.getPaymentsList().put(this.getCur_id(), newPayment);
        return result;
    }


}
