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
@Document(collection="buyer_info")
public class Buyer implements Serializable {

    private static final long serialVersionUID = 955728933773177978L;

    @Transient
    public static final String SEQUENCE_NAME = "buyer-info";

    @Id
    private Integer id;
    @Field
    private String firstName;
    @Field
    private String lastName;
    @Field
    private String address;
    @Field
    private String city;
    @Field
    private String state;
    @Field
    private Integer pin;
    @Field
    private String phone;
    @Field
    private String email;
}