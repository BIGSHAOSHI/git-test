package com.cskaoyan.order.converter;

import com.cskaoyan.order.dal.entitys.Order;
import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dal.entitys.OrderShipping;
import com.cskaoyan.order.dto.*;
import com.cskaoyan.order.dto.zyl.QueryOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({})
    OrderDetailResponse order2res(Order order);

    @Mappings({})
    OrderDetailInfo order2detail(Order order);

    @Mappings({})
    OrderItemDto item2dto(OrderItem item);

    @Mappings({})
    OrderShippingDto shipping2dto(OrderShipping shipping);


    List<OrderItemDto> item2dto(List<OrderItem> items);

    @Mappings({})
    OrderItemResponse item2res(OrderItem orderItem);

    @Mappings({})
    OrderDto order2dto(Order order);

    @Mappings(
            {
                    @Mapping(source = "orderShipping.receiverName", target = "userName"),
                    @Mapping(source = "order.userId", target = "userId"),
                    @Mapping(source = "orderShipping.receiverPhone", target = "tel"),
                    @Mapping(source = "orderShipping.receiverAddress", target = "streetName"),
                    @Mapping(source = "order.payment", target = "orderTotal"),
                    @Mapping(source = "order.status", target = "orderStatus"),
                    @Mapping(source = "orderItems",target = "goodsList")
            }
    )
    QueryOrderDto order2QueryOrderDto(Order order, List<OrderItem> orderItems, OrderShipping orderShipping);

}
