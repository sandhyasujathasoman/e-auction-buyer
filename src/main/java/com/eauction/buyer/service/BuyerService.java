package com.eauction.buyer.service;

import com.eauction.buyer.model.Buyer;

import java.util.List;
import java.util.Map;

/**
 * Buyer Service Interface to manage Buyer Details
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
public interface BuyerService {

    /**
     * Returns requested Buyer
     *
     * @param buyerIds refers to attribute {@code id}
     * @return a {@link Map} of {@link Buyer} identified by its id
     */
    Map<Integer, Buyer> getBuyers(List<Integer> buyerIds);

    /**
     * Returns requested Buyer
     *
     * @param buyerEmail refers to attribute {@code email}
     * @return a {@link Buyer} identified by its email
     */
    Buyer getBuyer(String buyerEmail);

    /**
     * Adds a new Buyer
     *
     * @param buyer refers to a new instance of {@link Buyer}
     * @return a newly added buyer of type {@link Buyer}
     */
    Buyer addBuyer(Buyer buyer);
}
