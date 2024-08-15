package com.webstrdy00.schedule_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ScheduleUpdateFailedException extends RuntimeException {
    public ScheduleUpdateFailedException(String message) {
        super(message);
    }

    public ScheduleUpdateFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
