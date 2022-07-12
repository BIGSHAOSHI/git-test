package com.cskaoyan.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @auther cskaoyan
 * @date 2022/4/24:15:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailTest {



    @Autowired
    JavaMailSender sender;

    @Test
    public void test() {
        System.out.println(sender);
    }
}
