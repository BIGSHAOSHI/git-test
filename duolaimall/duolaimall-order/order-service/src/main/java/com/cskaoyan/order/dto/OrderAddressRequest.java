package com.cskaoyan.order.dto;

import lombok.Data;

@Data
public class OrderAddressRequest {
    private String streetName;
    private String tel;
    private String userName;
    private Boolean isDefault;
}
