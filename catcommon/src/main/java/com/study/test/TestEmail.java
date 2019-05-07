package com.study.test;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by dd on 2019/4/28.
 */
public class TestEmail {

    public static void email() throws  Exception{
        String mailHost = "";
        String mailAccount = "";
        String mailPwd = "";
        String receiver="";
        String emailSubject = "你好";
        String emailContent = "很好";
        Properties prop = new Properties();
        prop.setProperty("mail.host", mailHost);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop);        // 根据参数配置，创建会话对象（为了发送邮件准备的）
        Transport ts = session.getTransport();
        ts.connect(mailHost, mailAccount, mailPwd);
        MimeMessage message = new MimeMessage(session);     // 创建邮件对象
        // 2. From: 发件人
        //    其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        //    真正要发送时, 邮箱必须是真实有效的邮箱。
        message.setFrom(new InternetAddress(mailAccount));
        // 3. To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver));
        // 4. Subject: 邮件主题
        message.setSubject(emailSubject, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(emailContent, "text/html;charset=UTF-8");
        // 6. 设置显示的发件时间
        message.setSentDate(new Date());
        // 7. 保存前面的设置
        message.saveChanges();
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    public static void main(String[] args) throws  Exception {
        email();
    }
}
