package com.cskaoyan.order.dal.persistence;


import com.cskaoyan.order.dal.entitys.Order;
import com.cskaoyan.order.dto.OrderAddressReponse;
import com.cskaoyan.order.dto.OrderAddressRequest;
import com.cskaoyan.order.dto.OrderDto;
import com.cskaoyan.order.dto.OrderItemDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {
    Long countAll();
    OrderDto selectOrderDto(@Param("userId") Long id);
    List<OrderAddressReponse> selectAllAddress(@Param("userId") Long userId);
    void insertOrderAddress(OrderAddressRequest request,@Param("userId") Long userId);
}