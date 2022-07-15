package com.eauction.buyer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Error Response class to manage all the technical exceptions
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer errorCode;
    private String errorStatus;
    private String errorMessage;
    private String errorPath;
    private Date errorOccurredTime;
}
