package com.processor.PaymentGateway.Services;

public class Pair<E1, E2> {
    private E1 e1;
    private E2 e2;

    public Pair(E1 e1, E2 e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public E1 getFirst() {
        return e1;
    }

    public E2 getSecond() {
        return e2;
    }

    public void setFirst(E1 e1) {
        this.e1 = e1;
        return;
    }

    public void setSecond(E2 e2) {
        this.e2 = e2;
        return;
    }

    public String toString() {
        return String.format("(%s, %s)", e1, e2);
    }
}