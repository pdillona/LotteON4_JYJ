package kr.co.lotteon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MailService {
    /*private final JavaMailSender javaMailSender;
    private static final String senderEmail= "wisejohn950330@gmail.com";
    private static int number;

    public static void createNumber(){
        number = ThreadLocalRandom.current().nextInt(100000, 1000000);
        String code = ""+number;
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("LotteON 이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail){
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);

        return number;
    }
    public int confirmCodeByEmail(String code) {

        if(code.equals(number)) {
            return 1;
        }else {
            return 0;
        }
    }*/
}