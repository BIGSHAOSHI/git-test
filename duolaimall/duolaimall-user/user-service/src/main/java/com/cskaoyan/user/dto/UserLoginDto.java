package com.cskaoyan.user.dto;

import lombok.Data;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/9 13:08
 * @Description:
 */

@Data
public class UserLoginDto {

    private Long id;
    private String username;
    private String phone;
    private String email;
    private String sex;
    private String address;
    private String file;
    private String description;
    private Integer points;
    private Long balance;
    private int state;
    private String token;
    private String code;
    private String msg;

}
