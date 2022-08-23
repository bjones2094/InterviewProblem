package com.jbhunt.interview.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ShopServiceClient {

    private RestTemplate restTemplate;
    private String baseUrl;

    public ShopServiceClient(RestTemplate restTemplate, @Value("${shop-service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public ShopDTO getShopByShopNumber(String shopNumber) {
        String url = this.baseUrl + "/shops/search";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("shopName", shopNumber);

        return restTemplate.getForObject(builder.buildAndExpand().toUri(), ShopDTO.class);
    }

    public ShopDTO getShopByShopId(Integer shopId) {
        String url = this.baseUrl + "/shops/{shopId}";
        return restTemplate.getForObject(url, ShopDTO.class, shopId);
    }
}
