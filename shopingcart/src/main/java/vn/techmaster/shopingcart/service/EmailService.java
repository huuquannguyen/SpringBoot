package vn.techmaster.shopingcart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("huuquan@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Shoping Bill");
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
