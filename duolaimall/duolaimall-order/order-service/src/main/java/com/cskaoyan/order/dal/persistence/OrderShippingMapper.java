package com.cskaoyan.order.dal.persistence;


import com.cskaoyan.order.dal.entitys.OrderShipping;
import com.cskaoyan.order.dto.OrderShippingDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OrderShippingMapper extends Mapper<OrderShipping> {
    OrderShippingDto selectOrderShippingDto(@Param("orderId") String orderId);
}