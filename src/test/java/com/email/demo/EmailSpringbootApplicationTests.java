package com.email.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailSpringbootApplicationTests {


    @Autowired
    JavaMailSenderImpl javaMailSender;

    /**
     * 发送简单邮件
     */
    @Test
    public void testSimpleMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("这个是标题");
        simpleMailMessage.setText("这个是测试内容");
        simpleMailMessage.setFrom("XXXX@126.com");
        simpleMailMessage.setTo("XXX@qq.com");
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送复杂邮件
     */
    @Test
    public void testMimeMail() throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //第二个参数 邮件是否要附件
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("it is also title ");
        //第二个参数 内容是否按html解析
        mimeMessageHelper.setText(" <b> it is a content </a>",true);
        mimeMessageHelper.setFrom("XXX@126.com");
        mimeMessageHelper.setTo("XXXX@qq.com");
        mimeMessageHelper.addAttachment("test.jpg",new File("C:\\Users\\WU\\Pictures\\banner.png"));

        javaMailSender.send(mimeMessage);
    }

}
