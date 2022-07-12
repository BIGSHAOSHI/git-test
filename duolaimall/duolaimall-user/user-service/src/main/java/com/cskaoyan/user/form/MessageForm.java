package com.cskaoyan.user.form;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/9 10:29
 * @Description:
 */

@Component
@Data
public class MessageForm  {

    @Value("${spring.mail.username}")
    String from;

    String to;

    String subject = "csmall商城激活";

    @Value("${email.text}")
    String  text;
}
