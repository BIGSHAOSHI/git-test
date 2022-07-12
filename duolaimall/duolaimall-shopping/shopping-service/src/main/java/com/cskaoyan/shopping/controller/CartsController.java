package com.cskaoyan.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 购物车相关操作
 * @author: 秦炯森
 * @date: 2022/7/11 9:05
 * @version: V1.0
 */
@RestController
public class CartsController {
    @Autowired
    ICartService cartService;

    // getCartListById
    @GetMapping("/shopping/carts")
    public ResponseData getCartList(HttpServletRequest servletRequest) {
        String user_info = servletRequest.getHeader("user_info");
        UserInfoDto userInfoDto = JSON.parseObject(user_info, UserInfoDto.class);
        CartListByIdRequest request = new CartListByIdRequest();
        request.setUserId(userInfoDto.getUid());
        CartListByIdResponse response = cartService.getCartListById(request);
//        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())){
        return new ResponseUtil<>().setData(response.getCartProductDtos());
//        }
    }

    @PostMapping("/shopping/carts")
    public ResponseData addToCart(@RequestBody AddCartRequest request) {
        AddCartResponse response = cartService.addToCart(request);
        return new ResponseUtil().setData(response);
    }

    @PutMapping("/shopping/carts")
    public ResponseData updateCartNum(@RequestBody UpdateCartNumRequest request) {
        UpdateCartNumResponse response = cartService.updateCartNum(request);
        return new ResponseUtil().setData(response);
    }

}
