package com.cskaoyan.user.service;

import com.cskaoyan.user.dto.*;

import javax.servlet.http.HttpServletRequest;


public interface IUserService {

    UserLoginResponse login(UserLoginRequest request);

    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    UserVerifyResponse verify(UserVerifyRequest request);

    UserLoginOutResponse loginOut(HttpServletRequest request);
}
