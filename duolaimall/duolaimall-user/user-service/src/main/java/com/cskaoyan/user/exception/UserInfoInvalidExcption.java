package com.cskaoyan.user.exception;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/9 14:23
 * @Description:
 */
public class UserInfoInvalidExcption extends RuntimeException{

    public UserInfoInvalidExcption() {
        super("用户信息不合法");
    }

    public UserInfoInvalidExcption(String message) {
        super(message);
    }

    public UserInfoInvalidExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInfoInvalidExcption(Throwable cause) {
        super(cause);
    }

    public UserInfoInvalidExcption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
