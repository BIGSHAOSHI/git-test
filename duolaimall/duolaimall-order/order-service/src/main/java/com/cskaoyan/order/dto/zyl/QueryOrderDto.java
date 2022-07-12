package com.cskaoyan.order.dto.zyl;

import com.cskaoyan.order.dto.OrderItemDto;
import lombok.Data;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author zyl
 * @since 2022/07/11 12:07
 */
@Data
public class QueryOrderDto {

    // 收货人姓名
    // OrderShipping
    private String userName;

    // 用户id
    // Order
    private Integer userId;

    // 收货电话
    // OrderShipping
    private String tel;

    // 收货地址
    // OrderShipping
    private String streetName;

    // 订单总价
    // Order
    private Integer orderTotal;

    // 订单状态
    // Order
    private Integer orderStatus;

    // 商品列表
    // OrderItem
    private List<OrderItemDto> goodsList;
}
