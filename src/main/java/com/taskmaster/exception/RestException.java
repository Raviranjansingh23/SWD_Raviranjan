package com.taskmaster.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6757164344942222022L;
	private final HttpStatus status;

    public RestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
