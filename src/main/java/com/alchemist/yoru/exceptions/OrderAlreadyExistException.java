package com.alchemist.yoru.exceptions;


public class OrderAlreadyExistException extends RuntimeException{
    public OrderAlreadyExistException(String message) {
        super(message);
    }
}
