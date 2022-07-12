package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * @Date2022/7/8 21:15
 * @Decription TODO
 * @Author dai_yuan
 */
@Data
public class ProductCateDtoResponse extends AbstractResponse {
    List<ProductCateDto> productCateDtos;
}
