package com.processor.PaymentGateway.Services;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class Payment {
    final int cardNumber;
    final String expiryDate;
    final double amount;
    final String currency;
    final Integer cvv;
    final Instant paymentTime;

    final List<String> currencyList;

    public Payment(int cardNumber, String expiryDate, double amount, String currency, Integer cvv){
        this.paymentTime = Instant.now();
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.currencyList = Arrays.asList("GBP", "USD", "EUR", "JPY", "BRL");
    }

    public int getCardNumber(){
        return cardNumber;
    }

    public Instant getPaymentTime() {
        return paymentTime;
    }

    public Integer getCvv() {
        return cvv;
    }

    public boolean performValidation(){
        int length = String.valueOf(cardNumber).length();
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
}

