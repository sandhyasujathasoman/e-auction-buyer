package com.eauction.buyer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    private static final long serialVersionUID = 955728933773177564L;

    private Integer id;
    private String productName;
    private String shortDescription;
    private String detailedDescription;
    private String category;
    private String startingPrice;
    private String bidEndDate;
    private Integer sellerId;

}
