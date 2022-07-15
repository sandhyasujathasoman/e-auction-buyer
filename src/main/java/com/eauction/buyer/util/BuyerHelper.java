package com.eauction.buyer.util;

import com.eauction.buyer.dto.BidBuyerRequest;
import com.eauction.buyer.model.Bid;
import com.eauction.buyer.model.Buyer;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Buyer Helper class to manage utility methods
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
public class BuyerHelper {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private BuyerHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static Buyer buildBuyer(BidBuyerRequest bidBuyerRequest) {
        Buyer buyer = null;
        if (Objects.nonNull(bidBuyerRequest) && Objects.nonNull(bidBuyerRequest.getBuyerRequest())) {
            buyer = Buyer.builder()
                    .firstName(bidBuyerRequest.getBuyerRequest().getFirstName())
                    .lastName(bidBuyerRequest.getBuyerRequest().getLastName())
                    .address(bidBuyerRequest.getBuyerRequest().getAddress())
                    .city(bidBuyerRequest.getBuyerRequest().getCity())
                    .state(bidBuyerRequest.getBuyerRequest().getState())
                    .pin(bidBuyerRequest.getBuyerRequest().getPin())
                    .phone(bidBuyerRequest.getBuyerRequest().getPhone())
                    .email(bidBuyerRequest.getBuyerRequest().getEmail())
                    .build();
        }
        return buyer;
    }

    public static Bid buildBid(BidBuyerRequest bidBuyerRequest, Integer buyerId) {
        Bid bid = null;
        if (Objects.nonNull(bidBuyerRequest) && Objects.nonNull(bidBuyerRequest.getBidRequest())) {
            bid = Bid.builder()
                    .productId(bidBuyerRequest.getBidRequest().getProductId())
                    .bidAmount(bidBuyerRequest.getBidRequest().getBidAmount())
                    .buyerId(buyerId)
                    .build();
        }
        return bid;
    }

    public static Date now() {
        return new Date();
    }

    public static Date toDate(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(true);
            date = dateFormat.parse(dateString);
        } catch (Exception exc) {
            return null;
        }
        return date;
    }

    public static boolean isFutureDate(Date date) {
        return isFutureDate(new Date(), date);
    }

    public static boolean isFutureDate(Date sourceDate, Date targetDate) {
        return stripTimeFromDate(sourceDate).before(stripTimeFromDate(targetDate));
    }

    private static Calendar stripTimeFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}
