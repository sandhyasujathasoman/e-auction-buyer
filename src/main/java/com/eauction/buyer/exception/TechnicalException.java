package com.eauction.buyer.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Technical Exception class to manage all the technical exceptions
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = 6853858869431791704L;

    private HttpStatus status;

    public TechnicalException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    public TechnicalException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.status = httpStatus;
    }
}
