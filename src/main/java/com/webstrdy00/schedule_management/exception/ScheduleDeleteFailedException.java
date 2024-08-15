package com.webstrdy00.schedule_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ScheduleDeleteFailedException extends RuntimeException {
    public ScheduleDeleteFailedException(String message) {
        super(message);
    }

    public ScheduleDeleteFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
