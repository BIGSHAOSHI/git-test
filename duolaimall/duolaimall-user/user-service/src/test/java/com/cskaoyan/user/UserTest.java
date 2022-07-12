package com.cskaoyan.user;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.mall.commons.util.jwt.JwtTokenUtils;
import com.cskaoyan.user.converter.UserConverterMapper;
import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dal.persistence.MemberMapper;
import com.cskaoyan.user.dto.UserInfoDto;
import com.cskaoyan.user.form.MessageForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/9 11:04
 * @Description:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    String from;

    @Value("${email.text}")
    String text;

    @Autowired
    UserConverterMapper userConverterMapper;

    /**
     * @description:  测试数据库插入能否成功
     *
     * @return: 测试通过
     * @author: 王世杰
     * @date: 2022/7/9 11:09
     * @creed: 禅
     */
    @Test
    public void testInsertSql(){

        Member member = new Member();
        member.setCreated(new Date());
        member.setUpdated(new Date());
        member.setEmail("test@test.com");
        member.setUsername("test1");
        member.setPassword("test1");
        member.setPhone("testPhone1");
        memberMapper.insertSelective(member);
        
    }

    /**
     * @description:  测试email发送
     * @return: 测试通过
     * @author: 王世杰
     * @date: 2022/7/9 11:11
     * @creed: 禅
     */
    @Test
    public void testMail(){
        MessageForm messageForm = new MessageForm();
        messageForm.setTo("h891616942@qq.com");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(messageForm.getTo());
        message.setSubject(messageForm.getSubject());
        String uuid = UUID.randomUUID().toString();
        message.setText(String.format(text,"test1",uuid));
        sender.send(message);
    }

    /**
     * @description:  测试JWT生成
     *
     * @return: 测试成功
     * @author: 王世杰
     * @date: 2022/7/9 13:50
     * @creed: 禅
     */
    @Test
    public void testJWT(){
        Member member = new Member();
        member.setUsername("bigshaoshi");
        member.setId(111L);
        UserInfoDto userInfo = userConverterMapper.converterToUserInfo(member);
        String jsonString = JSON.toJSONString(userInfo);
        String token1 = JwtTokenUtils.builder().msg(jsonString).build().creatJwtToken();
        String msg = JwtTokenUtils.builder().token(token1).build().freeJwt();
        System.out.println(msg);
    }

    @Test
    public void testJWT2(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjc2thb3lhbiIsImV4cCI6MTY1NzUyNTY0NiwidXNlciI6IjNFODhCMjI3RDVCNDM0NEUyREE3NERBNjcyQ0IyNTlCQTdBMDYyNkM3MzAwNEIwMjU3OUNGRUE3NzExMjY3Qzc0RUFGODU3RTNBQ0U4NDU5REVBOEM5QTNBQTBCRUE4MDk4QjkwMUM0MTgyMzdGRUE2REE3QUZBNzNCNDM1MkNBMTBDRTVFNjc3QjlFRTQwMDQyRjhEMDJEN0E0MTlGQURBMkE5REFGQTAxMjA2ODY1NDMxQTM2Q0UyMDU3Mjk2OUVBMjFCQzE3QjYwN0Q5RDEyMzI2RkREMEY5MTMwRTUxIn0.63rVOwbo0c6nUk_axU4OMtm0sU4z3_WF_KHX2nEdJL4";
        String  token1 = "eyJpc3MiOiJjc2thb3lhbiIsImV4cCI6MTY1NzUyNTY0NiwidXNlciI6IjNFODhCMjI3RDVCNDM0NEUyREE3NERBNjcyQ0IyNTlCQTdBMDYyNkM3MzAwNEIwMjU3OUNGRUE3NzExMjY3Qzc0RUFGODU3RTNBQ0U4NDU5REVBOEM5QTNBQTBCRUE4MDk4QjkwMUM0MTgyMzdGRUE2REE3QUZBNzNCNDM1MkNBMTBDRTVFNjc3QjlFRTQwMDQyRjhEMDJEN0E0MTlGQURBMkE5REFGQTAxMjA2ODY1NDMxQTM2Q0UyMDU3Mjk2OUVBMjFCQzE3QjYwN0Q5RDEyMzI2RkREMEY5MTMwRTUxIn0";
        //String s1 = JwtTokenUtils.builder().token(token).build().freeJwt();
        String s2 = JwtTokenUtils.builder().token(token1).build().freeJwt();
    }


}
