package com.cskaoyan.order.dal.persistence;

import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dto.OrderItemDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderItemMapper extends Mapper<OrderItem> {
    List<OrderItem> queryByOrderId(@Param("orderId") String orderId);
    void updateStockStatus(@Param("status") Integer status, @Param("orderId") String orderId);
    List<OrderItemDto> selectOrderItemDto(@Param("orderId") String orderId);
}