package com.cskaoyan.shopping.service.remote;

import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import com.cskaoyan.shopping.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther cskaoyan
 * @date 2022/6/16:16:33
 */

/*
      服务与服务之间的调用有关系
 */
@RestController
public class OrderProductServiceImpl{

    @Autowired
    ICartService iCartSevice;

    @RequestMapping(value = "/rpc/items",method = RequestMethod.DELETE)
    public ClearCartItemResponse clearCartItemByUserID(@RequestBody ClearCartItemRequest request) {
        return iCartSevice.clearCartItemByUserID(request);
    }

    @PostMapping(value = "/order/rpc/detail")
    public ProductDetailResponse getProductDetail(@RequestBody ProductDetailRequest request) {
        return null;
    }
}
