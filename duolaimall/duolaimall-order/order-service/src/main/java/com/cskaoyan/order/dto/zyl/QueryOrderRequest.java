package com.cskaoyan.order.dto.zyl;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.order.constant.OrderRetCode;
import lombok.Data;

/**
 * @author zyl
 * @since 2022/07/11 11:56
 */
@Data
public class QueryOrderRequest extends AbstractRequest {
    private String id;

    /**
     * 参数校验
     * @return void
     * @author zyl
     * @since 2022/07/11 12:06
     */
    @Override
    public void requestCheck() {
        if (id == null || "".equals(id)) {
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
