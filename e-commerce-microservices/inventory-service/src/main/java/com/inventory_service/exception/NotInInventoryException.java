package com.inventory_service.exception;

public class NotInInventoryException extends RuntimeException{

    public NotInInventoryException(String message){
        super(message);
    }

}
