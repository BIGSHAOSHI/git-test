package com.cskaoyan.shopping.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @auther cskaoyan
 * @date 2022/7/8:11:20
 */
@Data
public class TestProductDetailDto {

    String productName;

    BigDecimal price;

    String imgUrl;
}
