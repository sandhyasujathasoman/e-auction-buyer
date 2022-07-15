package com.eauction.buyer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Bid Request Class is to handle data transfer
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidRequest implements Serializable {

    private static final long serialVersionUID = 955728933773176528L;

    private Integer productId;
    private String bidAmount;
}
