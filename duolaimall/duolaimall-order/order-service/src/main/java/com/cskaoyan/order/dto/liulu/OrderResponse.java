package com.cskaoyan.order.dto.liulu;

import com.cskaoyan.order.dto.OrderDetailResponse;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    List<OrderDetailResponse> list;
    long total;
}
