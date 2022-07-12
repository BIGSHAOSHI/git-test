package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.dto.CartProductDto;
import com.cskaoyan.shopping.dto.CheckAllItemRequest;
import com.cskaoyan.shopping.dto.CheckAllItemResponse;
import com.cskaoyan.shopping.service.IProductCheckedService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductCheckedServiceImpl implements IProductCheckedService {
    @Autowired
    RedissonClient redissonClient;

    @Override
    public CheckAllItemResponse checkAllItems(CheckAllItemRequest request) {
        CheckAllItemResponse response = new CheckAllItemResponse();

        try {
            request.requestCheck();

            RMap<Long, CartProductDto> cart = redissonClient.getMap(request.getUserId() + "");
            cart.keySet().forEach(key -> {
                CartProductDto value = cart.get(key);
                value.setChecked(request.getChecked());
                cart.put(key,value);
            });

            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }

        return response;

    }
}
