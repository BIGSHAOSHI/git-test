package com.cskaoyan.shopping.service;

import com.cskaoyan.shopping.dto.TestProductDetailDto;
import com.cskaoyan.shopping.dto.TestProductDetailRequest;
import com.cskaoyan.shopping.dto.TestProductDetailResponse;

/**
 * @auther cskaoyan
 * @date 2022/7/8:11:19
 */
public interface ITestProductDetailService {

    TestProductDetailResponse getProductDetail(TestProductDetailRequest request);
}
