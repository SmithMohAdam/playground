package com.order_service.order_service.exception;

public class NotInInventoryException extends RuntimeException{

    public NotInInventoryException(String message){
        super(message);
    }

}
