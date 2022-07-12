package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @auther cskaoyan
 * @date 2022/6/17:9:51
 */
@Data
public class TestProductDetailResponse extends AbstractResponse {
    TestProductDetailDto productDetailDto;
}
