写在前面：本文中使用126邮箱进行测试

代码地址: https://github.com/Blankwhiter/email

# 一、注册邮箱以及开启POP3/SMTP/IMAP
读者请自行注册，以及自行开启POP3/SMTP/IMAP。
在开启POP3/SMTP/IMAP过程中，会要求输入一个授权码，请记住，接下来将使用该授权密码进行邮件发送。

# 二、编写springboot配置文件以及代码
1.引入email环境。
**pom.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.email</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>email-springboot</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--引入email环境-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>


</project>

```
2.编写配置文件
**application.yml**
```xml
spring:
  mail:
    username: XXXX@126.com
    password: XXXXX (第一步的授权密码)
    host: smtp.126.com

#    qq邮箱需要多设置如下
#    properties:
#              mail:
#                smtp:
#                  ssl:
#                    enable: true
```
3.测试代码
```java

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
        mimeMessageHelper.addAttachment("banner.png",new File("C:\\Users\\monkey\\Pictures\\banner.png"));

        javaMailSender.send(mimeMessage);
    }

}

```
