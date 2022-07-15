package com.eauction.buyer.dto;

import com.eauction.buyer.model.Buyer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Bid Response Class is to handle data transfer
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidResponse implements Serializable {

    private static final long serialVersionUID = 955728933773177448L;

    private Integer id;
    private Integer productId;
    private String bidAmount;
    private Buyer buyer;
}
