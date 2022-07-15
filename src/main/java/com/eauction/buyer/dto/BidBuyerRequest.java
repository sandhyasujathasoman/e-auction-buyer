package com.eauction.buyer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Bid and Buyer Request Class is to handle data transfer
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidBuyerRequest implements Serializable {

    private static final long serialVersionUID = 955728933664267418L;

    private BidRequest bidRequest;
    private BuyerRequest buyerRequest;
}
