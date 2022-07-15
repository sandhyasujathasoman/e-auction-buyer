package com.eauction.buyer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource Exist Exception class to manage all the resource that are already exist exceptions
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceExistException extends TechnicalException {

    private static final long serialVersionUID = -7206259820819733712L;

    public ResourceExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
