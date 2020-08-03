package com.chang.ccloud.common.utils;

import com.chang.ccloud.common.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author changxizhao
 * @Date 2020/8/2 21:36
 * @Description
 */
public class EmailUtil {

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void send(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        try {
            message.setFrom(from);
            message.setSubject(email.getSubject());
            message.setTo(email.getReceiver());
            message.setText(email.getText());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

}
