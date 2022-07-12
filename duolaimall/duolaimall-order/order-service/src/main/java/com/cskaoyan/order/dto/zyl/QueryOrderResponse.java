package com.cskaoyan.order.dto.zyl;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @author zyl
 * @since 2022/07/11 12:04
 */
@Data
public class QueryOrderResponse extends AbstractResponse {
    private QueryOrderDto queryOrderDto;
}
