package com.example.qpc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    private String authNum;

    // 랜덤 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }

        }
        authNum = key.toString();
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        // 인증 코드 생성
        createCode();
        // mailConfig에 작성한 이메일
        String setFrom = "dlkrog@naver.com";
        // 받는 사람 이메일
        String toEmail = email;
        // 제목
        String title = "이메일 인증 테스트 코드입니다.";

        MimeMessage message = javaMailSender.createMimeMessage();
        // 보낼 이메일 설정
        message.addRecipients(Message.RecipientType.TO,email);
        //제목 설정
        message.setSubject(title);
        // 보내는 이메일
        message.setFrom(setFrom);
        message.setText(setContext(authNum),"utf-8","html");

        return message;
    }

    // 실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException,UnsupportedEncodingException {

        // 메일 전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        // 실제 메일 전송
        javaMailSender.send(emailForm);

        // 인증코드 반환
        return authNum;
    }

    // 타임리프를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code",code);
        return templateEngine.process("mail",context); // mail.html
    }
}




