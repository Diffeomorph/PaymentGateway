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
    TreeMap<Integer, Pair<Payment,Boolean>> paymentsTreeMap2;
    int cur_id;

    public Payments(){
        this.cur_id = 0;
        this.paymentsTreeMap2 = new TreeMap<>();
    }

    public TreeMap<Integer, Pair<Payment,Boolean>> getPaymentsTreeMap() {
        return paymentsTreeMap2;
    }

    public TreeMap<Integer, Pair<Payment,Boolean>> getPaymentsMaskedList() throws CloneNotSupportedException {
        TreeMap<Integer,Pair<Payment,Boolean>> maskedPayments = new TreeMap<>();
        for (Integer key: paymentsTreeMap2.keySet()){
            Payment curPayment = (Payment) paymentsTreeMap2.get(key).getFirst().clone();
            boolean curSuccess = paymentsTreeMap2.get(key).getSecond();
            String maskedCurPaymentCardNumber = curPayment.maskCardNumber();
            Payment maskedCurPayment = new Payment(maskedCurPaymentCardNumber, curPayment.expiryDate, curPayment.amount, curPayment.currency, curPayment.cvv);
            Pair maskedPair = new Pair(maskedCurPayment, curSuccess);
            maskedPayments.put(key, maskedPair);
        }
        return maskedPayments;
    }

    public Payment getPaymentById(int id){
        return paymentsTreeMap2.get(id).getFirst();
    }

    public Payment getPaymentByIdMasked(int id) throws CloneNotSupportedException {
        Payment requestedPayment = (Payment) paymentsTreeMap2.get(id).getFirst().clone(); // cloned as don't want to alter the actual payment
        String maskedRequestedPaymentCardNumber = requestedPayment.maskCardNumber();
        Payment maskedRequestedPayment = new Payment(maskedRequestedPaymentCardNumber, requestedPayment.expiryDate, requestedPayment.amount, requestedPayment.currency, requestedPayment.cvv);
        return maskedRequestedPayment;
    }

    public void setPaymentsList(TreeMap<Integer, Pair<Payment,Boolean>> paymentsTreeMap){
        this.paymentsTreeMap2 = paymentsTreeMap;
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
        Pair newPair = new Pair(newPayment, false);
        if (result == true) {
            newPair.setSecond(true);
        }
        this.getPaymentsTreeMap().put(this.getCur_id(), newPair);
        return result;
    }


}
