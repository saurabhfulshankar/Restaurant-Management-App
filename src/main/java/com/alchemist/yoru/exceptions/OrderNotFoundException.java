package com.alchemist.yoru.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super("" + message);
    }
}