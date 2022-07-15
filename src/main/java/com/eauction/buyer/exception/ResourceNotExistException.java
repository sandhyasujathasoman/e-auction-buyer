package com.eauction.buyer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource Not Exist Exception class to manage all the resource not exist exceptions
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotExistException extends TechnicalException {

    private static final long serialVersionUID = -7206259820819733712L;

    public ResourceNotExistException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
