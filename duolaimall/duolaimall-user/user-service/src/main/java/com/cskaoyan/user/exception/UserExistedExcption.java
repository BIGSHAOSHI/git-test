package com.cskaoyan.user.exception;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/9 14:14
 * @Description:
 */
public class UserExistedExcption extends RuntimeException {

    public UserExistedExcption() {
        super("用户已存在");
    }

    public UserExistedExcption(String message) {
        super(message);
    }

    public UserExistedExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistedExcption(Throwable cause) {
        super(cause);
    }

    public UserExistedExcption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
