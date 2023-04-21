package com.example.pethospitalbackend.util;

import com.example.pethospitalbackend.exception.SendMailFailedException;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;import java.util.Properties;

@Component
public class EmailUtil {

  public String sendMail(String recipient)throws GeneralSecurityException {
    String code = "";

//    Properties props = new Properties();
//    props.setProperty("mail.host", "smtp.163.com");
//    props.setProperty("mail.smtp.auth", "true");
//    props.setProperty("mail.transport.protocol", "SMTP");

    MailSSLSocketFactory sf = new MailSSLSocketFactory();
    sf.setTrustAllHosts(true);
    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "SMTP");
    props.setProperty("mail.host", "smtp.163.com");
    props.setProperty("mail.smtp.auth", "true");// 指定验证为true是否需要身份验证
    props.setProperty("mail.smtp.ssl.enable", "true");
    props.put("mail.smtp.ssl.socketFactory", sf);


    // 确定权限（账号和密码）
    Authenticator authenticator =
        new Authenticator() {
          @Override
          public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("volunteer88888888@163.com", "OLWYQXFJKKTIRCGS");
          }
        };

    try {
      String from = "volunteer88888888@163.com";
      // 1 获得连接
      Session session = Session.getDefaultInstance(props, authenticator);
      // 2 创建消息
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));

      message.addRecipients(
          MimeMessage.RecipientType.CC,
          InternetAddress.parse(from)); // 设置在发送给收信人之前给自己（发送方）抄送一份，不然会被当成垃圾邮件，报554错
      // message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
      message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(recipient));
      // message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
      // 主题（标题）
      message.setSubject("ChangePassWord ");

      // 生成6位验证码
      code = RandomStringUtils.randomNumeric(6);

      //  正文
      String str = " Your verify code is：" + code + ".This verify code is valid in 30 minutes!";
      // 设置编码，防止发送的内容中文乱码。
      message.setContent(str, "text/html;charset=UTF-8");
      // 3 发送消息
      Transport.send(message);

    } catch (MessagingException e) {
      e.printStackTrace();
      throw new SendMailFailedException("邮件发送失败");
    }
    return code;
  }
}
