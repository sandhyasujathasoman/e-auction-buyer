package com.eauction.buyer.service.impl;

import com.eauction.buyer.config.SellerServiceConfig;
import com.eauction.buyer.dto.Product;
import com.eauction.buyer.exception.*;
import com.eauction.buyer.model.Bid;
import com.eauction.buyer.repo.BidRepository;
import com.eauction.buyer.service.BidService;
import com.eauction.buyer.service.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.*;

import static com.eauction.buyer.util.BuyerHelper.*;

/**
 * Bid Service Implementation Class to manage Bid Details
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Service
public class BidServiceImpl implements BidService {

    private static final String PARAM_PRODUCT_ID = "product-id";
    private static final String FIELD_PRODUCT_ID = "productId";
    private static final String FIELD_BUYER_ID = "buyerId";
    private static final String FIELD_BID_AMOUNT = "bidAmount";

    @Resource
    private BidRepository bidRepository;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private SellerServiceConfig sellerServiceConfig;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    RestTemplate restTemplate;
    
    
    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }
    
    @Override
    public List<Bid> getAllBids(Integer buyerOrProductId, boolean isProduct) {
        if (isProduct) {
            return bidRepository.findByProductId(buyerOrProductId);
        } else {
            return bidRepository.findByBuyerId(buyerOrProductId);
        }
    }

    @Override
    public Bid addBid(Bid bid) {
        try {
            validateNewBidAndThrowException(bid);
            bid.setId(sequenceService.getNextSequence(Bid.SEQUENCE_NAME));
            bid = bidRepository.save(bid);
        } catch (Exception exc) {
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
        }
        return bid;
    }

    @Override
    public Bid updateBid(Integer buyerId, Integer productId, String newBidAmount) {
        Bid updatedBid;
        try {
            validateUpdateBidAndThrowException(buyerId, productId);
            Query query = new Query(Criteria.where(FIELD_PRODUCT_ID).is(productId).and(FIELD_BUYER_ID).is(buyerId));
            Update update = new Update().set(FIELD_BID_AMOUNT, newBidAmount);
            updatedBid = mongoOperations.findAndModify(query, update,
                    FindAndModifyOptions.options().returnNew(true), Bid.class);
        } catch (Exception exc) {
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
        }
        return updatedBid;
    }

    /**
     * Validates and Throw Exception for the new Bid entry
     *
     * @param bid refers to type {@link Bid}
     */
    private void validateNewBidAndThrowException(Bid bid) {
        Product product = getProductFromSellerService(bid.getProductId());
        if (Objects.isNull(product)) {
            throw new ResourceNotExistException(String.format("The bid cannot be placed as the product doesn't exist " +
                    "[productId: %s]", bid.getProductId()));
        }
        Query query = new Query(Criteria.where(FIELD_PRODUCT_ID).is(bid.getProductId()).and(FIELD_BUYER_ID).is(bid.getBuyerId()));
        Bid existingBid = mongoOperations.findOne(query, Bid.class);
        if (Objects.nonNull(existingBid)) {
            throw new ResourceExistException(String.format("The bid cannot be placed as there is an existing bid " +
                    "available for the given product [productId: %s, productName: %s, bidAmount: %s, buyerId: %s]",
                    existingBid.getProductId(), product.getProductName(), existingBid.getBidAmount(), existingBid.getBuyerId()));
        }
        // Validate BidEndDate
        if (!isFutureDate(now(), toDate(product.getBidEndDate()))) {
            throw new InvalidDataException(String.format("The bid cannot be placed as the product's bidEndDate is in " +
                    "the past from the current date [bidEndDate: %s]", product.getBidEndDate()));
        }
        // Validate Bid Amount
        if (StringUtils.isBlank(bid.getBidAmount())
                || !StringUtils.isNumeric(bid.getBidAmount())
                || Integer.valueOf(bid.getBidAmount()) < Integer.valueOf(product.getStartingPrice())) {
            throw new InvalidDataException(String.format("The bid cannot be placed as the bidAmount is either empty " +
                    "or not numeric or lesser than the product's startingPrice [bidAmount: %s]", bid.getBidAmount()));
        }
    }

    private void validateUpdateBidAndThrowException(Integer buyerId, Integer productId) {
        // Validates Bid exist
        Query query = new Query(Criteria.where(FIELD_PRODUCT_ID).is(productId).and(FIELD_BUYER_ID).is(buyerId));
        Bid existingBid = mongoOperations.findOne(query, Bid.class);
        if (Objects.isNull(existingBid)) {
            throw new ResourceNotExistException(String.format("The bid cannot be updated as there is no bid exist for " +
                    "the given product [productId: %s, buyerId: %s]", productId, buyerId));
        } else {
            Product product = getProductFromSellerService(productId);
            // Validates Product still exist
            if (Objects.isNull(product)) {
                throw new ResourceNotExistException(String.format("The bid cannot be updated as the product no more " +
                        "exist [productId: %s]", productId));
            }
            // Validates BidEndDate
            if (!isFutureDate(now(), toDate(product.getBidEndDate()))) {
                throw new InvalidOperationException(String.format("The bid cannot be updated as the bidEndDate is " +
                        "in the past from the current date [bidEndDate: %s]", product.getBidEndDate()));
            }
        }
    }

    private Product getProductFromSellerService(Integer productId) {
        Product product = null;
        if (Objects.nonNull(productId)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            Map<String, Integer> params = new HashMap<>();
            params.put(PARAM_PRODUCT_ID, productId);

            URI endpointUri = UriComponentsBuilder.newInstance()
                    .scheme(sellerServiceConfig.getScheme())
                    .host(sellerServiceConfig.getHost())
                    .port(sellerServiceConfig.getPort())
                    .path(sellerServiceConfig.getProductSearch())
                    .buildAndExpand(params).toUri();

            // Invokes Seller Service to retrieve Product
            ResponseEntity<Product> responseEntity = restTemplate.exchange(endpointUri, HttpMethod.GET,
                    httpEntity, Product.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()
                    && responseEntity.hasBody()) {
                product = responseEntity.getBody();
            }
        }
        return product;
    }

}
