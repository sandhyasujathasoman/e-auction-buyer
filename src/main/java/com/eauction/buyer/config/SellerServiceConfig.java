package com.eauction.buyer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Seller Service Configuration class
 *
 * @author Sandhya S S
 * @since 15/06/2022
 */
@Data
@ConfigurationProperties(prefix = "app.services.seller-service")
public class SellerServiceConfig {
    private String scheme;
    private String host;
    private Integer port = -1;
    private String productSearch;
}
