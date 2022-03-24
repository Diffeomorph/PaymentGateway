package com.processor.PaymentGateway.Services;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.lang.String;

public class Payment implements Cloneable {
    String cardNumber;
    final String expiryDate;
    final double amount;
    final String currency;
    final Integer cvv;
    final Instant paymentTime;
    boolean success;

    final List<String> currencyList;

    public Payment(String cardNumber, String expiryDate, double amount, String currency, Integer cvv, boolean success){
        this.paymentTime = Instant.now();
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.success = false;
        this.currencyList = Arrays.asList("GBP", "USD", "EUR", "JPY", "CHF", "NZD", "AUD","KRW","CNY");
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public Instant getPaymentTime() {
        return paymentTime;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean performValidation(){
        int length = cardNumber.length();
        if (length != 16){
            return false;
        }
        if (expiryDate.charAt(2) != '/' || Integer.parseInt(expiryDate.substring(0,2)) < 0 || Integer.parseInt(expiryDate.substring(0,2)) > 12 || Integer.parseInt(expiryDate.substring(3)) < 0 || Integer.parseInt(expiryDate.substring(3)) > 50) {
            return false;
        }
        if (0 > amount || amount > Double.MAX_VALUE){
            return false;
        }
        if (currencyList.contains(currency) == false){
            return false;
        }
        if (cvv < 0 || cvv > 999){
            return false;
        }
        return true;
    }

    public void maskCardNumber(){
        this.cardNumber = "############" + this.cardNumber.substring(cardNumber.length() - 4);
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

