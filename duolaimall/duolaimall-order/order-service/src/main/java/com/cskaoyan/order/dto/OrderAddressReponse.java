package com.cskaoyan.order.dto;

import lombok.Data;

@Data
public class OrderAddressReponse {
    private Boolean idDefault;
    private Long addressId;
    private String streetName;
    private String tel;
    private Long userId;
    private String userName;
}
