package com.processor.PaymentGateway.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.TreeMap;

public final class Payments {
    TreeMap<Integer, Payment> paymentsList;
    int cur_id;

    Payments(){
        this.cur_id = 0;
        this.paymentsList = new TreeMap<>();
    }

    public TreeMap<Integer, Payment> getPayments() {
        return paymentsList;
    }

    public String printPayments() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String currentPayments = mapper.writeValueAsString(paymentsList);
        return currentPayments;
    }

    public TreeMap<Integer, Payment> getPaymentsList() {
        return paymentsList;
    }

    public Payment getPaymentById(int id){
        return paymentsList.get(id);
    }

    public int getCur_id(){
        return cur_id;
    }

    public void setCur_id(int cur_id) {
        this.cur_id = cur_id;
    }


}
