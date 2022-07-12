package com.cskaoyan.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.commons.exception.ValidateException;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.commons.util.jwt.JwtTokenUtils;
import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.converter.UserConverterMapper;
import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dal.entitys.UserVerify;
import com.cskaoyan.user.dal.persistence.MemberMapper;
import com.cskaoyan.user.dal.persistence.UserVerifyMapper;
import com.cskaoyan.user.dto.*;
import com.cskaoyan.user.exception.UserExistedExcption;
import com.cskaoyan.user.exception.UserInfoInvalidExcption;
import com.cskaoyan.user.service.IUserService;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/8 21:50
 * @Description:
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    UserVerifyMapper userVerifyMapper;

    @Autowired
    JavaMailSender sender;

    @Autowired
    UserConverterMapper userConverterMapper;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${email.text}")
    private String text;

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        UserLoginResponse response = new UserLoginResponse();

        try {
            request.requestCheck();

            Example example = new Example(Member.class);
            example.createCriteria().andEqualTo("username",request.getUserName()).andEqualTo("password",request.getPassword());
            List<Member> users = memberMapper.selectByExample(example);
            if (users.size() == 0){
                response.setCode(UserRetCode.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(UserRetCode.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            if (users.size() == 1){
                Member user = users.get(0);
                if ("N".equals(user.getIsVerified())){
                    response.setCode(UserRetCode.USER_ISVERFIED_ERROR.getCode());
                    response.setMsg(UserRetCode.USER_ISVERFIED_ERROR.getMessage());
                    return response;
                }
                response = userConverterMapper.converter(user);
                UserInfoDto userInfo = userConverterMapper.converterToUserInfo(user);

                String toJSONString = JSON.toJSONString(userInfo);
                String access_token = JwtTokenUtils.builder().msg(toJSONString).build().creatJwtToken();

                response.setToken(access_token);
                response.setCode(UserRetCode.SUCCESS.getCode());
                response.setMsg(UserRetCode.SUCCESS.getMessage());
            }

        } catch (ValidateException e){
            response.setCode(UserRetCode.REQUEST_CHECK_FAILURE.getCode());
            response.setMsg(UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }

        return response;

    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {

        UserRegisterResponse response = new UserRegisterResponse();

        try {

            request.requestCheck();

            Example example = new Example(Member.class);
            example.createCriteria().orEqualTo("username",request.getUserName()).orEqualTo("email",request.getEmail());
            List<Member> members = memberMapper.selectByExample(example);
            if (members.size() > 0){
                response.setCode(UserRetCode.USERNAME_ALREADY_EXISTS.getCode());
                response.setMsg(UserRetCode.USERNAME_ALREADY_EXISTS.getMessage());
                return response;
            }

            // 用户信息不合法 ： 校验 密码
            if (!userCheck(request.getUserPwd())){
                response.setCode(UserRetCode.USER_INFOR_INVALID.getCode());
                response.setMsg(UserRetCode.USER_INFOR_INVALID.getMessage());
                return response;
            }

            Member member = new Member();
            member.setUsername(request.getUserName());
            member.setPassword(request.getUserPwd());
            member.setEmail(request.getEmail());
            member.setCreated(new Date());
            member.setUpdated(new Date());

            int row1 = memberMapper.insertSelective(member);

            String uuid = UUID.randomUUID().toString();
            UserVerify userVerify = new UserVerify();
            userVerify.setUsername(request.getUserName());
            userVerify.setIsVerify("N");
            userVerify.setIsExpire("N");
            userVerify.setUuid(uuid);

            int row2 = userVerifyMapper.insertSelective(userVerify);


            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(request.getEmail());
            message.setSubject("csmall商城验证");
            message.setText(String.format(text,request.getUserName(),uuid));
            sender.send(message);

            response.setCode(UserRetCode.SUCCESS.getCode());
            response.setMsg(UserRetCode.SUCCESS.getMessage());
            return response;

        } catch (ValidateException e){
            response.setCode(UserRetCode.REQUEST_CHECK_FAILURE.getCode());
            response.setMsg(UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        } catch (Exception e) {
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        response.setCode(UserRetCode.USER_REGISTER_FAILED.getCode());
        response.setMsg(UserRetCode.USER_REGISTER_FAILED.getMessage());
        return response;
    }

    private boolean userCheck(String password) {
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public UserVerifyResponse verify(UserVerifyRequest request) {
        UserVerifyResponse response = new UserVerifyResponse();

        try {
            request.requestCheck();
            Example example = new Example(UserVerify.class);
            example.createCriteria().andEqualTo("username",request.getUserName()).andEqualTo("uuid",request.getUuid());
            List<UserVerify> verifies = userVerifyMapper.selectByExample(example);
            if (verifies.size() == 0){
                response.setCode(UserRetCode.USERVERIFY_INFOR_INVALID.getCode());
                response.setMsg(UserRetCode.USERVERIFY_INFOR_INVALID.getMessage());
                return response;
            }

            UserVerify userVerify = new UserVerify();
            userVerify.setIsVerify("Y");
            userVerify.setRegisterDate(new Date());
            int row1 = userVerifyMapper.updateByExampleSelective(userVerify, example);


            Example memberExample = new Example(Member.class);
            memberExample.createCriteria().andEqualTo("username",request.getUserName());
            Member member = new Member();
            member.setIsVerified("Y");
            member.setUpdated(new Date());
            int row2 = memberMapper.updateByExampleSelective(member, memberExample);
            if (row1 == 0 || row2 == 0){
                response.setCode(UserRetCode.USER_REGISTER_VERIFY_FAILED.getCode());
                response.setMsg(UserRetCode.USER_REGISTER_VERIFY_FAILED.getMessage());
                return response;
            }
            response.setCode(UserRetCode.SUCCESS.getCode());
            response.setMsg(UserRetCode.SUCCESS.getMessage());


        } catch (ValidateException e){
            response.setCode(UserRetCode.REQUEST_CHECK_FAILURE.getCode());
            response.setMsg(UserRetCode.REQUEST_CHECK_FAILURE.getMessage());
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        } catch (Exception e) {

            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response,e);

        }
        return response;
    }

    @Override
    public UserLoginOutResponse loginOut(HttpServletRequest request) {

        // 清空session域里面的数据即可
        UserLoginOutResponse response = new UserLoginOutResponse();

        HttpSession session = request.getSession();
        session.invalidate();

        // TODO 用户退出 这里需要携带一个为空的access_token
        String access_token = "";
        response.setToken(access_token);
        response.setCode(UserRetCode.SUCCESS.getCode());
        response.setMsg(UserRetCode.SUCCESS.getMessage());

        return response;
    }
}
