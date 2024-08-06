package com.t1.mail_api.service;

import jakarta.mail.internet.MimeMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${mail.from}")
    private String mailFrom;
    @Value("${mail.from.name}")
    private String mailFromName;



    public void sendMail(String[] recipient, String subject, String content, String inputStream)
            throws Exception {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(mailFrom, mailFromName);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            if (inputStream != null) {
                messageHelper.addAttachment("reportForm.xlsx",
                        new ByteArrayResource(IOUtils.toByteArray(inputStream)));
            }
        };

        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            //System.out.println(e);
            // runtime exception; compiler will not force you to handle it
        }
    }


    public void sendMail(String[] recipient, String subject, String content)
            throws Exception {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(mailFrom, mailFromName);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

        };

        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            //System.out.println(e);
            // runtime exception; compiler will not force you to handle it
        }
    }
}
