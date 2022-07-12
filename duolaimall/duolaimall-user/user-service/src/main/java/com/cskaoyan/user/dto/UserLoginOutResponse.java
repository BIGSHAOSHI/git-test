package com.cskaoyan.user.dto;

import com.cskaoyan.mall.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @author DaneHuang
 * @since 2022/07/10 15:02
 */
@Data
public class UserLoginOutResponse extends AbstractResponse {
    private String token;
}
