package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.converter.ITestProductConverter;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.TestProductDetailDto;
import com.cskaoyan.shopping.dto.TestProductDetailRequest;
import com.cskaoyan.shopping.dto.TestProductDetailResponse;
import com.cskaoyan.shopping.service.ITestProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther cskaoyan
 * @date 2022/7/8:11:24
 */
@Service
public class ITestProductDetailServiceImpl implements ITestProductDetailService {


    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ITestProductConverter productConverter;

    @Override
    public TestProductDetailResponse getProductDetail(TestProductDetailRequest request) {

        TestProductDetailResponse response = new TestProductDetailResponse();

        try {
            // 参数校验
            request.requestCheck();
            Item item = itemMapper.selectByPrimaryKey(request.getProductId());
            TestProductDetailDto testProductDetailDto = productConverter.productDoToDto(item);

            //执行成功
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
            response.setProductDetailDto(testProductDetailDto);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
