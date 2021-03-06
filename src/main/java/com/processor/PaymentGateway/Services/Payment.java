package com.processor.PaymentGateway.Services;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.lang.String;

/**
 * The Payment class provide the structure for each individual payment. It also stores whether the transaction has
 * been successful or not.
 */
public final class Payment implements Cloneable {
    final String cardNumber;
    final String expiryDate;
    final double amount;
    final String currency;
    final Integer cvv;
    final Instant paymentTime;

    // list of allowed currencies for the transaction
    final List<String> currencyList;

    public Payment(String cardNumber, String expiryDate, double amount, String currency, Integer cvv){
        this.paymentTime = Instant.now();
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.currencyList = Arrays.asList("GBP", "USD", "EUR", "JPY", "CHF", "NZD", "AUD","KRW","CNY");
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String maskCardNumber(){
        String maskedCardNumber = "############" + this.cardNumber.substring(cardNumber.length() - 4);
        return maskedCardNumber;
    }

    public Instant getPaymentTime() {
        return paymentTime;
    }

    public Integer getCvv() {
        return cvv;
    }

    // check whether the transaction/payment is valid or not
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

    // add functionality to make class cloneable (deep copy)
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

