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
    static int instanceCounter = 0;

    private int counter = 0;
    private TreeMap<Integer, Pair<Payment,Boolean>> paymentsTreeMap;

    public Payments(){
        instanceCounter++;
        counter = instanceCounter;
        this.paymentsTreeMap = new TreeMap<>();
    }

    public TreeMap<Integer, Pair<Payment,Boolean>> getPaymentsTreeMapNoMasking() {
        return paymentsTreeMap;
    }

    public TreeMap<Integer, Pair<Payment,Boolean>> getPaymentsTreeMap() throws CloneNotSupportedException {
        TreeMap<Integer,Pair<Payment,Boolean>> maskedPayments = new TreeMap<>();
        for (Integer key: paymentsTreeMap.keySet()){
            Payment curPayment = (Payment) paymentsTreeMap.get(key).getFirst().clone();
            boolean curSuccess = paymentsTreeMap.get(key).getSecond();
            String maskedCurPaymentCardNumber = curPayment.maskCardNumber();
            Payment maskedCurPayment = new Payment(maskedCurPaymentCardNumber, curPayment.expiryDate, curPayment.amount, curPayment.currency, curPayment.cvv);
            Pair maskedPair = new Pair(maskedCurPayment, curSuccess);
            maskedPayments.put(key, maskedPair);
        }
        return maskedPayments;
    }

    public Payment getPaymentByIdNoMasking(int id){
        return paymentsTreeMap.get(id).getFirst();
    }

    public Payment getPaymentById(int id) throws CloneNotSupportedException {
        Payment requestedPayment = (Payment) paymentsTreeMap.get(id).getFirst().clone(); // cloned as don't want to alter the actual payment
        String maskedRequestedPaymentCardNumber = requestedPayment.maskCardNumber();
        Payment maskedRequestedPayment = new Payment(maskedRequestedPaymentCardNumber, requestedPayment.expiryDate, requestedPayment.amount, requestedPayment.currency, requestedPayment.cvv);
        return maskedRequestedPayment;
    }

    public void setPaymentsList(TreeMap<Integer, Pair<Payment,Boolean>> paymentsTreeMap){
        this.paymentsTreeMap = paymentsTreeMap;
    }

    public int getCur_id(){
        return this.counter;
    }

    public boolean submitPayment(Payment newPayment){
        if (newPayment.performValidation() == false){
            return false;
        }
        String uri = "http://localhost:8080/transactions";
        RestTemplate restTemplate = new RestTemplate();
        boolean result = restTemplate.postForObject(uri,null,boolean.class);
        Pair newPair = new Pair(newPayment, false);
        if (result == true) {
            newPair.setSecond(true);
        }
        this.getPaymentsTreeMapNoMasking().put(this.getCur_id(), newPair);
        return result;
    }


}
