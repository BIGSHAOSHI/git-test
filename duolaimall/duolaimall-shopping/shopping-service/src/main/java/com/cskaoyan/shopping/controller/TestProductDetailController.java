package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.dto.TestProductDetailRequest;
import com.cskaoyan.shopping.dto.TestProductDetailResponse;
import com.cskaoyan.shopping.service.ITestProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestProductDetailController {

    @Autowired
    ITestProductDetailService productDetailService;




    @GetMapping("/shopping/test")
    public ResponseData testGetProductDetail( Long productId) {

        TestProductDetailRequest request = new TestProductDetailRequest();
        request.setProductId(productId);
        TestProductDetailResponse response = productDetailService.getProductDetail(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())) {
            // 执行成功
            return new ResponseUtil().setData(response.getProductDetailDto());
        }

        // 执行失败
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

}
