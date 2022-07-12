package com.cskaoyan.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.order.constant.OrderRetCode;
import com.cskaoyan.order.api.ProductApi;
import com.cskaoyan.order.dto.*;
import com.cskaoyan.order.dto.liulu.OrderResponse;
import com.cskaoyan.order.dto.zyl.CancelOrDeleteOrderDto;
import com.cskaoyan.order.dto.zyl.QueryOrderDto;
import com.cskaoyan.order.dto.zyl.QueryOrderRequest;
import com.cskaoyan.order.dto.zyl.QueryOrderResponse;
import com.cskaoyan.order.form.OrderPageInfoRequest;
import com.cskaoyan.order.form.PageResponse;
import com.cskaoyan.order.service.OrderCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/shopping")
public class OrderController {
    /**
    * @Author: liulu
    * @Description: 展示订单
    * @DateTime: 2022/7/9 9:44
    * @Params:
    * @Return
    */
    @Autowired
    OrderCoreService orderCoreService;

    @Autowired
    ProductApi productApi;
    @GetMapping("/order")
    public ResponseData orderShow(OrderPageInfoRequest request, HttpServletRequest servletrequest){
        PageResponse response = orderCoreService.displayAllOrder(request, servletrequest);
        return new ResponseUtil().setData(response);
    }
    @PostMapping("/order")
    public ResponseData addOrder(@RequestBody CreateOrderRequest request,HttpServletRequest servletRequest) {
        String user_info = servletRequest.getHeader("user_info");
        JSONObject jsonObject = JSON.parseObject(user_info);
        long uid = Long.parseLong(jsonObject.get("uid").toString());
        request.setUserId(uid);
        request.setUniqueKey(UUID.randomUUID().toString());
        CreateOrderResponse response = orderCoreService.createOrder(request);
        if (response.getCode().equals(OrderRetCode.SUCCESS.getCode())) {
            return new ResponseUtil<>().setData(response.getOrderId());
        }
        return new ResponseUtil<>().setErrorMsg(response.getMsg());
    }
    /**
     * 查询订单详情
     * @return com.cskaoyan.mall.commons.result.ResponseData
     * @author zyl
     * @since 2022/07/11 11:52
     */
    @GetMapping("/order/{id}")
    public ResponseData queryOrder(@PathVariable String id){
        QueryOrderRequest request = new QueryOrderRequest();
        request.setId(id);
        QueryOrderResponse queryOrderResponse = orderCoreService.queryOrder(request);
        if (OrderRetCode.SUCCESS.getCode().equals(queryOrderResponse.getCode())){
            return new ResponseUtil<QueryOrderDto>().setData(queryOrderResponse.getQueryOrderDto());
        }
        return new ResponseUtil<String>().setErrorMsg(queryOrderResponse.getMsg());
    }

    /**
     * 取消订单
     * @param map
     * @return com.cskaoyan.mall.commons.result.ResponseData
     * @author zyl
     * @since 2022/07/11 14:34
     */
    @PostMapping("/cancelOrder")
    public ResponseData cancelOrder(@RequestBody Map map){
        String id = (String) map.get("id");
        CancelOrderRequest request = new CancelOrderRequest();
        request.setOrderId(id);
        CancelOrderResponse response = orderCoreService.cancelOrder(request);
        if (OrderRetCode.SUCCESS.getCode().equals(response.getCode())){
            return new ResponseUtil<CancelOrDeleteOrderDto>().setData(response.getCancelOrderDto());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 删除订单
     * @param id
     * @return com.cskaoyan.mall.commons.result.ResponseData
     * @author zyl
     * @since 2022/07/11 17:49
     */
    @DeleteMapping("/order/{id}")
    public ResponseData deleteOrder(@PathVariable String id) {
        DeleteOrderRequest request = new DeleteOrderRequest();
        request.setOrderId(id);
        DeleteOrderResponse response = orderCoreService.deleteOrder(request);
        if (OrderRetCode.SUCCESS.getCode().equals(response.getCode())){
            return new ResponseUtil<CancelOrDeleteOrderDto>().setData(response.getDeleteOrderDto());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
