package com.eauction.buyer.service.impl;

import com.eauction.buyer.model.IdSequence;
import com.eauction.buyer.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Sequence Service Implementation class to generate custom sequences
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private MongoOperations mongoOperations;

    public Integer getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);
        IdSequence sequenceCounter = mongoOperations.findAndModify(query, update,
                FindAndModifyOptions.options().returnNew(true).upsert(true), IdSequence.class);
        return Objects.nonNull(sequenceCounter) ? sequenceCounter.getSequence() : 1;
    }
}
