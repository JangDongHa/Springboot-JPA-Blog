package com.dong.newBlog.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dong.newBlog.model.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import helper.MailUtils;



@Service("mss")
public class MailSendService {
	@Value("${gmailPassword.key}")
	private String gmailKey;
	
    private JavaMailSenderImpl mailSender;
    
    private int size;
    
    private void setMailSender(String host, int port, String username, String password) {
    	mailSender.setHost(host);
    	mailSender.setPort(port);
    	mailSender.setUsername(username);
    	mailSender.setPassword(password);
    	Properties properties = new Properties();
    	properties.setProperty("mail.smtp.auth", "true");
    	properties.setProperty("mail.smtp.starttls.enable", "true");
    	properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
    	properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
    	mailSender.setJavaMailProperties(properties);
    }

    //인증키 생성
    private String getKey(int size) {
        this.size = size;
        return getAuthCode();
    }

    //인증코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }

    //인증메일 보내기
    @Transactional
    public String sendAuthMail(User user) {
        //6자리 난수 인증번호 생성
    	String authKey = getKey(6);
        mailSender = new JavaMailSenderImpl();
        setMailSender("smtp.gmail.com", 587, "jdh3340@gmail.com", gmailKey);

        //인증메일 보내기
        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("회원가입 이메일 인증");
            sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
            .append("<p>인증번호 : " + authKey + "</p>")
            .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
            .append("<a href='http://localhost:8000/auth/signupConfirm?authKey="+authKey+"&email="+user.getEmail())
            .append("' target='_blenk'>이메일 인증 확인</a>")
            .toString());
            sendMail.setFrom("jdh3340@gmail.com", "관리자");
            sendMail.setTo(user.getEmail());
            sendMail.send();
            
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

          return authKey;
    }
}