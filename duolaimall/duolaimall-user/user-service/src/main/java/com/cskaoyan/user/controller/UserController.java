package com.cskaoyan.user.controller;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.commons.util.CookieUtil;
import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.dto.*;
import com.cskaoyan.user.service.IKaptchaService;
import com.cskaoyan.user.service.IUserService;
import com.cskaoyan.user.service.impl.KaptchaServiceImpl;
import com.netflix.client.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpCookie;
import java.util.Map;

import static com.sun.javaws.JnlpxArgs.verify;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/8 20:20
 * @Description: 用户的注册、登录、验证登录
 */

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    IUserService userService;

    @Autowired
    IKaptchaService kaptchaService;


    /**
     * @description:  登录验证
     *
     * @author: 王世杰
     * @date: 2022/7/9 10:41
     * @creed: 禅
     */
    @GetMapping("login")
    public ResponseData loginCheck(HttpServletRequest request){

        String user_info = request.getHeader("user_info");
        if (user_info == null || user_info.length() == 0){
            return new ResponseUtil<>().setErrorMsg("token校验失败2");
        }
        UserInfoDto userInfoDto = JSON.parseObject(user_info, UserInfoDto.class);
        return new ResponseUtil<>().setData(userInfoDto);
    }


    @PostMapping("login")
    public ResponseData login(@RequestBody Map map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        UserLoginRequest request = new UserLoginRequest();
        KaptchaCodeRequest kaptchaCodeRequest = new KaptchaCodeRequest();
        UserLoginResponse response = null;
        try {
            String uuid = CookieUtil.getCookieValue(httpServletRequest, "kaptcha_uuid");
            kaptchaCodeRequest.setUuid(uuid);
            // 用户输入的验证码
            String captcha = (String) map.get("captcha");
            kaptchaCodeRequest.setCode(captcha);
            KaptchaCodeResponse Kresponse = kaptchaService.validateKaptchaCode(kaptchaCodeRequest);

            if (!(Kresponse.getCode().equals(UserRetCode.SUCCESS.getCode()))) {
                return new ResponseUtil<>().setErrorMsg(UserRetCode.KAPTCHA_CODE_ERROR.getMessage());
            }

            request.setUserName((String) map.get("userName"));
            request.setPassword((String) map.get("userPwd"));
            response = userService.login(request);
            if (UserRetCode.SUCCESS.getCode().equals(response.getCode())){
                Cookie jwtCookie = new Cookie("access_token",response.getToken());
                jwtCookie.setPath("/");
                httpServletResponse.addCookie(jwtCookie);
                return new ResponseUtil<>().setData(response);
            } else if ( UserRetCode.USER_INFOR_INVALID.getCode().equals(response.getCode())
                    || UserRetCode.REQUEST_CHECK_FAILURE.getCode().equals(response.getCode())
                    || UserRetCode.USERORPASSWORD_ERRROR.getCode().equals(response.getCode())
                    || UserRetCode.USER_ISVERFIED_ERROR.getCode().equals(response.getCode())
            ){
                return new ResponseUtil<>().setErrorMsg(response.getMsg());

            }
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return new ResponseUtil<>().setErrorMsg("登录失败");
    }


    @PostMapping("register")
    public ResponseData register(@RequestBody UserRegisterRequest userRegisterRequest){
        UserRegisterResponse response = userService.register(userRegisterRequest);

        if (UserRetCode.SUCCESS.getCode().equals(response.getCode())){
            return new ResponseUtil<>().setData(null);
        } else if (UserRetCode.USERNAME_ALREADY_EXISTS.getCode().equals(response.getCode())
                || UserRetCode.USER_INFOR_INVALID.getCode().equals(response.getCode())
                || UserRetCode.REQUEST_CHECK_FAILURE.getCode().equals(response.getCode())
                || UserRetCode.USER_REGISTER_FAILED.getCode().equals(response.getCode())
        ){
            return new ResponseUtil<>().setErrorMsg(response.getMsg());
        }
        return new ResponseUtil<>().setErrorMsg("注册失败");
    }

    @RequestMapping("verify")
    public ResponseData veryfy(String username,String uuid){

        UserVerifyRequest request = new UserVerifyRequest();
        request.setUserName(username);
        request.setUuid(uuid);
        UserVerifyResponse response = userService.verify(request);
        if (UserRetCode.SUCCESS.getCode().equals(response.getCode())){
            return new ResponseUtil<>().setData(null);
        } else if (UserRetCode.USER_REGISTER_VERIFY_FAILED.getCode().equals(response.getCode())
                || UserRetCode.REQUEST_CHECK_FAILURE.getCode().equals(response.getCode())
                || UserRetCode.USERVERIFY_INFOR_INVALID.getCode().equals(response.getCode())
        ){
            return new ResponseUtil<>().setErrorMsg(response.getMsg());
        }
        return new ResponseUtil<>().setErrorMsg("邮箱验证失败");
    }

    @GetMapping("loginOut")
    public ResponseData loginOut(HttpServletRequest request,HttpServletResponse httpResponse) {

        UserLoginOutResponse response = userService.loginOut(request);

        // 设置cookie 返回给前端
        Cookie accessToken = new Cookie("access_token", response.getToken());
        accessToken.setPath("/");
        httpResponse.addCookie(accessToken);

        return new ResponseUtil().setData(response.getMsg());
    }


}
