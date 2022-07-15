package com.eauction.buyer.service;

import com.eauction.buyer.model.Bid;
import com.eauction.buyer.model.Buyer;

import java.util.List;

/**
 * Bid Service Interface to manage Bid Details
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
public interface BidService {
	
	/**
     * Returns all Bids
     *
     * @return a list of {@link Bid}
     */
    List<Bid> getAllBids();
    
    /**
     * Returns all Bids for the given Buyer or Product
     *
     * @param buyerOrProductId refers to attribute {@code buyerId} or {@code productId}
     * @param isProduct refers to a boolean value to denote the id is for a Product or a Buyer
     * @return a list of {@link Bid}
     */
    List<Bid> getAllBids(Integer buyerOrProductId, boolean isProduct);


    /**
     * Adds a new Bid
     *
     * @param bid refers to a new instance of {@link Bid}
     * @return a newly added bid of type {@link Bid}
     */
    Bid addBid(Bid bid);

    /**
     * Updates an existing Bid
     *
     * @param buyerId refers to attribute {@code id} of type {@link Buyer}
     * @param productId refers to attribute {@code productId} of type {@link Bid}
     * @param newBidAmount refers to attribute {@code bidAmount} of type {@link Bid}
     */
    Bid updateBid(Integer buyerId, Integer productId, String newBidAmount);
}
