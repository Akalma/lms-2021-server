package com.lms.app.value;

public class InvalidPayloadException extends RuntimeException{
    private static final long serialVersionUID = 961459571618836379L;
    public InvalidPayloadException(String message){
        super(message);
    }
}
