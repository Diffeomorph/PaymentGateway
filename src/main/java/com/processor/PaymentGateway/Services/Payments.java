package com.processor.PaymentGateway.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.TreeMap;

/**
 * The Payments class provide the structure to hold all attempted payments. It also provides functionality to retrieve
 * previous transactions and mask the card number.
 */
@Service
public final class Payments {
    TreeMap<Integer, Payment> paymentsTreeMap;
    int cur_id;

    public Payments(){
        this.cur_id = 0;
        this.paymentsTreeMap = new TreeMap<>();
    }

    public TreeMap<Integer, Payment> getPaymentsTreeMap() {
        return paymentsTreeMap;
    }

    public TreeMap<Integer, Payment> getPaymentsMaskedList() throws CloneNotSupportedException {
        TreeMap<Integer,Payment> maskedPayments = new TreeMap<>();
        for (Integer key: paymentsTreeMap.keySet()){
            Payment curPayment = (Payment) paymentsTreeMap.get(key).clone();
            String maskedCurPaymentCardNumber = curPayment.maskCardNumber();
            Payment maskedCurPayment = new Payment(maskedCurPaymentCardNumber, curPayment.expiryDate, curPayment.amount, curPayment.currency, curPayment.cvv, curPayment.success);
            maskedPayments.put(key, maskedCurPayment);
        }
        return maskedPayments;
    }

    public Payment getPaymentById(int id){
        return paymentsTreeMap.get(id);
    }

    public Payment getPaymentByIdMasked(int id) throws CloneNotSupportedException {
        Payment requestedPayment = (Payment) paymentsTreeMap.get(id).clone(); // cloned as don't want to alter the actual payment
        String maskedRequestedPaymentCardNumber = requestedPayment.maskCardNumber();
        Payment maskedRequestedPayment = new Payment(maskedRequestedPaymentCardNumber, requestedPayment.expiryDate, requestedPayment.amount, requestedPayment.currency, requestedPayment.cvv, requestedPayment.success);
        return maskedRequestedPayment;
    }

    public void setPaymentsList(TreeMap<Integer, Payment> paymentsTreeMap){
        this.paymentsTreeMap = paymentsTreeMap;
    }

    public int getCur_id(){
        return this.cur_id;
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
        this.getPaymentsTreeMap().put(this.getCur_id(), newPayment);
        return result;
    }


}
