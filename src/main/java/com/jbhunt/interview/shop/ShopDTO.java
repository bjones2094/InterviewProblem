package com.jbhunt.interview.shop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDTO {
    @JsonProperty("shopId")
    private Integer shopId;
    @JsonProperty("shopNumber")
    private String shopNumber;
    @JsonProperty("availableServices")
    private List<String> availableServices;
    @JsonProperty("address")
    private String address;
}
