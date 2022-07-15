package com.eauction.buyer.repo;

import com.eauction.buyer.model.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends MongoRepository<Bid, Integer> {

    List<Bid> findByBuyerId(Integer buyerId);

    List<Bid> findByProductId(Integer productId);
}
