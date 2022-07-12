package com.cskaoyan.order.api;

import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("duolai-shopping")
public interface ProductApi{
    /**
     * 清空指定用户的购物车缓存(用户下完订单之后清理）
     */
    @RequestMapping(value = "/rpc/items",method = RequestMethod.DELETE)
    ClearCartItemResponse clearCartItemByUserID(@RequestBody ClearCartItemRequest request);

    /*
        提供给订单服务查询指定商品信息
     */
    @PostMapping(value = "/order/rpc/detail")
    ProductDetailResponse getProductDetail(@RequestBody ProductDetailRequest request);
}
