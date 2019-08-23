package com.jeyu.memegen.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by MateuszJ
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EncodingException extends RuntimeException {
    public EncodingException(String message){
        super(message);
    }
}
