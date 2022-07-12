package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.IProductDeleteInCartService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
public class ProductDeleteInCartServiceImpl implements IProductDeleteInCartService {

    @Autowired
    RedissonClient redissonClient;
    @Override
    public DeleteCartItemResponse deleteProductInCartByProductId(DeleteCartItemRequest request) {
        DeleteCartItemResponse response = new DeleteCartItemResponse();
        try {
            request.requestCheck();

            RMap<Object, Object> cart = redissonClient.getMap(request.getUserId() + "");
            CartProductDto cartProductDto = (CartProductDto) cart.get(request.getItemId());
            if (cartProductDto == null){
                response.setCode(ShoppingRetCode.DB_EXCEPTION.getCode());
                response.setMsg(ShoppingRetCode.DB_EXCEPTION.getMessage());
                return response;
            }
            
            cart.remove(request.getItemId());
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public DeleteCheckedItemResposne deleteCheckedItemsByUid(DeleteCheckedItemRequest request) {
        DeleteCheckedItemResposne resposne = new DeleteCheckedItemResposne();

        try {
            request.requestCheck();

            RMap<Long, CartProductDto> cart = redissonClient.getMap(request.getUserId() + "");

            Set<Long> keySet = cart.keySet();

            keySet.forEach(pid -> {
                if ( "true".equals(cart.get(pid).getChecked())){
                    cart.remove(pid);
                }
            });

            resposne.setCode(ShoppingRetCode.SUCCESS.getCode());
            resposne.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(resposne,e);
        }

        return resposne;
    }

    @Override
    public ClearCartItemResponse clearCartItemByUserID(ClearCartItemRequest request) {
        ClearCartItemResponse resposne = new ClearCartItemResponse();

        try {
            request.requestCheck();

            RMap<Long, CartProductDto> cart = redissonClient.getMap(request.getUserId() + "");

            Set<Long> keySet = cart.keySet();

            keySet.forEach(pid -> {
                if ( "true".equals(cart.get(pid).getChecked())){
                    cart.remove(pid);
                }
            });

            resposne.setCode(ShoppingRetCode.SUCCESS.getCode());
            resposne.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(resposne,e);
        }

        return resposne;
    }

}
