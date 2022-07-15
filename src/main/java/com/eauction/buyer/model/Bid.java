package com.eauction.buyer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection="bid_info")
public class Bid implements Serializable {

    private static final long serialVersionUID = 955728933773177564L;

    @Transient
    public static final String SEQUENCE_NAME = "bid-info";

    @Id
    private Integer id;
    @Field
    private Integer productId;
    @Field
    private String bidAmount;
    @Field
    private Integer buyerId;

}
