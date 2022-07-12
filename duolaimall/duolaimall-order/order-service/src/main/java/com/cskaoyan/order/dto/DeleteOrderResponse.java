package com.cskaoyan.order.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import com.cskaoyan.order.dto.zyl.CancelOrDeleteOrderDto;
import lombok.Data;

@Data
public class DeleteOrderResponse extends AbstractResponse {
    private CancelOrDeleteOrderDto deleteOrderDto;
}
