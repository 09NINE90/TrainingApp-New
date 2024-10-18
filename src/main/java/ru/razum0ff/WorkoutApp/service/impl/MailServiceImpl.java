package ru.razum0ff.WorkoutApp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.enumiration.MailType;
import ru.razum0ff.WorkoutApp.service.MailService;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(UserEntity user, MailType type, Properties properties) {
        switch (type) {
            case REGISTRATION -> sendRegistrationEmail(user, properties);
            default -> {}
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(UserEntity user, Properties properties){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Аккаунт зарегистрирован! " + user.getFirstName());
        helper.setTo(user.getUsername());
        String emailContent = getRegistrationEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(UserEntity user){
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getFirstName());
        model.put("email", user.getUsername());
        model.put("password", user.getPassword());
        configuration.getTemplate("registration.html")
                .process(model,stringWriter);
        return stringWriter.getBuffer().toString();
    }
}