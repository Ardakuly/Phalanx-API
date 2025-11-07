package com.backbone.phalanx.notification.notification.service.implementation;

import com.backbone.phalanx.notification.notification.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendVerificationMail(String email, String verificationToken) {
        try {

            String subject = "Phalanx: Проверка электронной почты";
            String path = "/api/public/email/verify";
            String message = "Нажмите кнопку ниже, чтобы подтвердить свой адрес электронной почты:";
            String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).queryParam(
                    "token", verificationToken
            ).toUriString();

            String content = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; background-color: #f9f9f9; text-align: center;">
                        <h2 style="color: #333;">%s</h2>
                        <p style="font-size: 16px; color: #555;">%s</p>
                        <a href="%s" style="display: inline-block; margin: 20px 0; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;">Продолжить</a>
                        <p style="font-size: 14px; color: #777;">Или скопируйте и вставьте эту ссылку в адресную строку браузера:</p>
                        <p style="font-size: 14px; color: #007bff;">%s</p>
                        <p style="font-size: 12px; color: #aaa;">Это автоматическое сообщение. Пожалуйста, не отвечайте.</p>
                    </div>
                """.formatted(subject, message, actionUrl, actionUrl);

            send(email, subject, content);

        } catch (Exception exception) {
            log.error("Failed to send email: {}", exception.getMessage(), exception);
        }
    }

    @Override
    public void sendPasswordResetMail(String email, String code) {
        try {
            String content = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto;
                            padding: 20px; border-radius: 8px; background-color: #f9f9f9; text-align: center;">
                    <h2 style="color: #333;">Восстановление пароля</h2>
                    <p style="font-size: 16px; color: #555;">
                        Используйте этот код для восстановления пароля:
                    </p>
                    <div style="font-size: 32px; font-weight: bold; color: #007bff; margin: 20px 0;">
                        %s
                    </div>
                    <p style="font-size: 14px; color: #777;">
                        Код действует 10 минут. Если вы не запрашивали восстановление — просто проигнорируйте это письмо.
                    </p>
                </div>
            """.formatted(code);

            send(email, "Восстановление пароля", content);

        } catch (Exception exception) {
            log.error("Failed to send reset code email: {}", exception.getMessage(), exception);
        }
    }

    private void send(String email, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setText(content, true);
        mailSender.send(mimeMessage);
    }
}