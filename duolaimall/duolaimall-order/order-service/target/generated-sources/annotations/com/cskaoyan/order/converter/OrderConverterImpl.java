package com.cskaoyan.order.converter;

import com.cskaoyan.order.dal.entitys.Order;
import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dal.entitys.OrderShipping;
import com.cskaoyan.order.dto.OrderDetailInfo;
import com.cskaoyan.order.dto.OrderDetailResponse;
import com.cskaoyan.order.dto.OrderDto;
import com.cskaoyan.order.dto.OrderItemDto;
import com.cskaoyan.order.dto.OrderItemResponse;
import com.cskaoyan.order.dto.OrderShippingDto;
import com.cskaoyan.order.dto.zyl.QueryOrderDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T15:10:56+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_333 (Oracle Corporation)"
)
@Component
public class OrderConverterImpl implements OrderConverter {

    @Override
    public OrderDetailResponse order2res(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();

        orderDetailResponse.setOrderId( order.getOrderId() );
        orderDetailResponse.setPayment( order.getPayment() );
        orderDetailResponse.setPaymentType( order.getPaymentType() );
        orderDetailResponse.setPostFee( order.getPostFee() );
        orderDetailResponse.setStatus( order.getStatus() );
        orderDetailResponse.setCreateTime( order.getCreateTime() );
        orderDetailResponse.setUpdateTime( order.getUpdateTime() );
        orderDetailResponse.setPaymentTime( order.getPaymentTime() );
        orderDetailResponse.setConsignTime( order.getConsignTime() );
        orderDetailResponse.setEndTime( order.getEndTime() );
        orderDetailResponse.setCloseTime( order.getCloseTime() );
        orderDetailResponse.setShippingName( order.getShippingName() );
        orderDetailResponse.setShippingCode( order.getShippingCode() );
        orderDetailResponse.setUserId( order.getUserId() );
        orderDetailResponse.setBuyerMessage( order.getBuyerMessage() );
        orderDetailResponse.setBuyerNick( order.getBuyerNick() );
        orderDetailResponse.setBuyerComment( order.getBuyerComment() );

        return orderDetailResponse;
    }

    @Override
    public OrderDetailInfo order2detail(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();

        orderDetailInfo.setOrderId( order.getOrderId() );
        orderDetailInfo.setPayment( order.getPayment() );
        orderDetailInfo.setPaymentType( order.getPaymentType() );
        orderDetailInfo.setPostFee( order.getPostFee() );
        orderDetailInfo.setStatus( order.getStatus() );
        orderDetailInfo.setCreateTime( order.getCreateTime() );
        orderDetailInfo.setUpdateTime( order.getUpdateTime() );
        orderDetailInfo.setPaymentTime( order.getPaymentTime() );
        orderDetailInfo.setConsignTime( order.getConsignTime() );
        orderDetailInfo.setEndTime( order.getEndTime() );
        orderDetailInfo.setCloseTime( order.getCloseTime() );
        orderDetailInfo.setShippingName( order.getShippingName() );
        orderDetailInfo.setShippingCode( order.getShippingCode() );
        orderDetailInfo.setUserId( order.getUserId() );
        orderDetailInfo.setBuyerMessage( order.getBuyerMessage() );
        orderDetailInfo.setBuyerNick( order.getBuyerNick() );
        orderDetailInfo.setBuyerComment( order.getBuyerComment() );

        return orderDetailInfo;
    }

    @Override
    public OrderItemDto item2dto(OrderItem item) {
        if ( item == null ) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setId( item.getId() );
        if ( item.getItemId() != null ) {
            orderItemDto.setItemId( String.valueOf( item.getItemId() ) );
        }
        orderItemDto.setOrderId( item.getOrderId() );
        orderItemDto.setNum( item.getNum() );
        orderItemDto.setTitle( item.getTitle() );
        if ( item.getPrice() != null ) {
            orderItemDto.setPrice( BigDecimal.valueOf( item.getPrice() ) );
        }
        if ( item.getTotalFee() != null ) {
            orderItemDto.setTotalFee( BigDecimal.valueOf( item.getTotalFee() ) );
        }
        orderItemDto.setPicPath( item.getPicPath() );

        return orderItemDto;
    }

    @Override
    public OrderShippingDto shipping2dto(OrderShipping shipping) {
        if ( shipping == null ) {
            return null;
        }

        OrderShippingDto orderShippingDto = new OrderShippingDto();

        orderShippingDto.setOrderId( shipping.getOrderId() );
        orderShippingDto.setReceiverName( shipping.getReceiverName() );
        orderShippingDto.setReceiverPhone( shipping.getReceiverPhone() );
        orderShippingDto.setReceiverMobile( shipping.getReceiverMobile() );
        orderShippingDto.setReceiverState( shipping.getReceiverState() );
        orderShippingDto.setReceiverCity( shipping.getReceiverCity() );
        orderShippingDto.setReceiverDistrict( shipping.getReceiverDistrict() );
        orderShippingDto.setReceiverAddress( shipping.getReceiverAddress() );
        orderShippingDto.setReceiverZip( shipping.getReceiverZip() );

        return orderShippingDto;
    }

    @Override
    public List<OrderItemDto> item2dto(List<OrderItem> items) {
        if ( items == null ) {
            return null;
        }

        List<OrderItemDto> list = new ArrayList<OrderItemDto>( items.size() );
        for ( OrderItem orderItem : items ) {
            list.add( item2dto( orderItem ) );
        }

        return list;
    }

    @Override
    public OrderItemResponse item2res(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemResponse orderItemResponse = new OrderItemResponse();

        orderItemResponse.setId( orderItem.getId() );
        if ( orderItem.getItemId() != null ) {
            orderItemResponse.setItemId( String.valueOf( orderItem.getItemId() ) );
        }
        orderItemResponse.setOrderId( orderItem.getOrderId() );
        orderItemResponse.setNum( orderItem.getNum() );
        orderItemResponse.setTitle( orderItem.getTitle() );
        if ( orderItem.getPrice() != null ) {
            orderItemResponse.setPrice( BigDecimal.valueOf( orderItem.getPrice() ) );
        }
        if ( orderItem.getTotalFee() != null ) {
            orderItemResponse.setTotalFee( BigDecimal.valueOf( orderItem.getTotalFee() ) );
        }
        orderItemResponse.setPicPath( orderItem.getPicPath() );

        return orderItemResponse;
    }

    @Override
    public OrderDto order2dto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId( order.getOrderId() );
        orderDto.setPayment( order.getPayment() );
        orderDto.setPaymentType( order.getPaymentType() );
        orderDto.setPostFee( order.getPostFee() );
        orderDto.setStatus( order.getStatus() );
        orderDto.setCreateTime( order.getCreateTime() );
        orderDto.setUpdateTime( order.getUpdateTime() );
        orderDto.setPaymentTime( order.getPaymentTime() );
        orderDto.setConsignTime( order.getConsignTime() );
        orderDto.setEndTime( order.getEndTime() );
        orderDto.setCloseTime( order.getCloseTime() );
        orderDto.setShippingName( order.getShippingName() );
        orderDto.setShippingCode( order.getShippingCode() );
        orderDto.setUserId( order.getUserId() );
        orderDto.setBuyerMessage( order.getBuyerMessage() );
        orderDto.setBuyerNick( order.getBuyerNick() );
        orderDto.setBuyerComment( order.getBuyerComment() );

        return orderDto;
    }

    @Override
    public QueryOrderDto order2QueryOrderDto(Order order, List<OrderItem> orderItems, OrderShipping orderShipping) {
        if ( order == null && orderItems == null && orderShipping == null ) {
            return null;
        }

        QueryOrderDto queryOrderDto = new QueryOrderDto();

        if ( order != null ) {
            queryOrderDto.setOrderStatus( order.getStatus() );
            if ( order.getUserId() != null ) {
                queryOrderDto.setUserId( order.getUserId().intValue() );
            }
            if ( order.getPayment() != null ) {
                queryOrderDto.setOrderTotal( order.getPayment().intValue() );
            }
        }
        if ( orderItems != null ) {
            queryOrderDto.setGoodsList( item2dto( orderItems ) );
        }
        if ( orderShipping != null ) {
            queryOrderDto.setStreetName( orderShipping.getReceiverAddress() );
            queryOrderDto.setTel( orderShipping.getReceiverPhone() );
            queryOrderDto.setUserName( orderShipping.getReceiverName() );
        }

        return queryOrderDto;
    }
}
