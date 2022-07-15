package com.eauction.buyer;

import com.eauction.buyer.config.SellerServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableConfigurationProperties({
		SellerServiceConfig.class})
public class EAuctionBuyerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionBuyerServiceApplication.class, args);
	}

}


