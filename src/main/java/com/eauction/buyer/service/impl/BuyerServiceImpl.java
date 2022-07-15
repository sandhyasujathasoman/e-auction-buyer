package com.eauction.buyer.service.impl;

import com.eauction.buyer.exception.InvalidDataException;
import com.eauction.buyer.exception.ResourceNotExistException;
import com.eauction.buyer.exception.TechnicalException;
import com.eauction.buyer.model.Buyer;
import com.eauction.buyer.repo.BuyerRepository;
import com.eauction.buyer.service.BuyerService;
import com.eauction.buyer.service.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.*;

/**
 * Buyer Service Implementation Class to manage Buyer Details
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Service
public class BuyerServiceImpl implements BuyerService {

    @Resource
    private BuyerRepository buyerRepository;
    @Autowired
    private SequenceService sequenceService;

    @Override
    public Map<Integer, Buyer> getBuyers(List<Integer> buyerIds) {
        Map<Integer, Buyer> buyerMap = new HashMap<>(0);
        try {
            Iterable<Buyer> buyers = buyerRepository.findAllById(buyerIds);
            buyers.forEach(buyer -> buyerMap.put(buyer.getId(), buyer));
        } catch (Exception exc) {
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
        }
        return buyerMap;
    }

    @Override
    public Buyer getBuyer(String buyerEmail) {
        Buyer buyer;
        try {
            Optional<Buyer> buyerDoc = buyerRepository.findByEmail(buyerEmail);
            if (buyerDoc.isEmpty()) {
                throw new ResourceNotExistException(String.format("The requested Buyer doesn't exist [buyerEmail: %s]", buyerEmail));
            } else {
                buyer = buyerDoc.get();
            }
        } catch (Exception exc) {
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
        }
        return buyer;
    }

    @Override
    public Buyer addBuyer(Buyer buyer) {
        try {
            validateNewBuyerAndThrowException(buyer);
            buyer.setId(sequenceService.getNextSequence(Buyer.SEQUENCE_NAME));
            buyer = buyerRepository.save(buyer);
        } catch (Exception exc) {
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
        }
        return buyer;
    }

    /**
     * Validates and Throw Exception for the new Buyer entry
     *
     * @param buyer refers to type {@link Buyer}
     */
    private void validateNewBuyerAndThrowException(Buyer buyer) {
        Optional<Buyer> buyerDoc = buyerRepository.findByEmail(buyer.getEmail());
        if (buyerDoc.isPresent()) {
            buyer.setId(buyerDoc.get().getId());
        }
        // Validates FirstName
        if (StringUtils.isBlank(buyer.getFirstName())
                || (StringUtils.length(buyer.getFirstName()) < 5
                    || StringUtils.length(buyer.getFirstName()) > 30)) {
            throw new InvalidDataException(String.format("The buyer cannot be added as the firstName parameter is " +
                    "either empty or not as per specified length as between 5 and 30 [firstName: %s, length: %s]",
                    buyer.getFirstName(), StringUtils.length(buyer.getFirstName())));
        }
        // Validates LastName
        if (StringUtils.isBlank(buyer.getLastName())
                || (StringUtils.length(buyer.getLastName()) < 5
                    || StringUtils.length(buyer.getLastName()) > 25)) {
            throw new InvalidDataException(String.format("The buyer cannot be added as the lastName parameter is " +
                            "either empty or not as per specified length as between 5 and 25 [lastName: %s, length: %s]",
                    buyer.getLastName(), StringUtils.length(buyer.getLastName())));
        }
        // Validates Phone
        if (StringUtils.isBlank(buyer.getPhone())
                || !StringUtils.isNumeric(buyer.getPhone())
                || StringUtils.length(buyer.getPhone()) < 10
                || StringUtils.length(buyer.getPhone()) > 10) {
            throw new InvalidDataException(String.format("The buyer cannot be added as the phone parameter is " +
                            "either empty or not numeric or not as per specified length as 10 [phone: %s, length: %s]",
                    buyer.getPhone(), StringUtils.length(buyer.getPhone())));
        }
        // Validates Email
        if (StringUtils.isBlank(buyer.getEmail())
                || !EmailValidator.getInstance().isValid(buyer.getEmail())) {
            throw new InvalidDataException(String.format("The buyer cannot be added as the email parameter is " +
                            "either empty or not a valid email address [email: %s]", buyer.getEmail()));
        }
    }
}
