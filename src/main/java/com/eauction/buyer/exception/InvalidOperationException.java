package com.eauction.buyer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Operation Exception class to manage invalid operation
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidOperationException extends TechnicalException {

    private static final long serialVersionUID = -7206259820819733598L;

    public InvalidOperationException(String message) {
        super(message, HttpStatus.PRECONDITION_FAILED);
    }
}
