package com.cskaoyan.shopping.dto;

import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.AbstractRequest;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import lombok.Data;



/**
 * @auther cskaoyan
 * @date 2022/7/8:14:22
 */
@Data
public class TestProductDetailRequest extends AbstractRequest {
    Long productId;

    @Override
    public void requestCheck() {
        if (productId == null || productId < 0) {
            // 参数非法
            throw new ValidateException(ShoppingRetCode.PARAM_VALIDATE_FAILD.getCode(),
                    ShoppingRetCode.PARAM_VALIDATE_FAILD.getMessage());
        }
    }
}
