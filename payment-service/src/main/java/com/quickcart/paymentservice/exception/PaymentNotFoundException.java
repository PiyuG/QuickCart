package com.quickcart.paymentservice.exception;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(String message){
        super(message);
    }
}
