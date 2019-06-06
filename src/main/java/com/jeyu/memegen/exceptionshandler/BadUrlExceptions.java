package com.jeyu.memegen.exceptionshandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BadUrlExceptions extends RuntimeException {

    public BadUrlExceptions(){
        super("Bad url formed");
    }

}
