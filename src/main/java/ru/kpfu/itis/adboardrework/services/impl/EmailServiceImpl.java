package ru.kpfu.itis.adboardrework.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import ru.kpfu.itis.adboardrework.models.State;
import ru.kpfu.itis.adboardrework.models.User;
import ru.kpfu.itis.adboardrework.repositories.UserRepository;
import ru.kpfu.itis.adboardrework.services.EmailService;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine = createTemplateEngine();
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void confirmAccount(String hashForConfirm) {
        User user = userRepository.findByHashForConfirm(hashForConfirm).orElseThrow(
                () -> new UsernameNotFoundException("Not found")
        );

        if (user.getState() != State.NOT_CONFIRMED) {
            //TODO: поменять на норм ошибку когда сделаю хэндлер
            throw new UsernameNotFoundException("");
        }
        if (!user.getHashForConfirm().equals(hashForConfirm)) {
            //TODO: поменять на норм ошибку когда сделаю хэндлер
            throw new UsernameNotFoundException("");
        }
        user.setState(State.ACTIVE);
        userRepository.save(user);
    }


    @Override
    public void sendEmail(String to, String subject, String confirmationLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            Context context = new Context();
            context.setVariable("confirmationLink", confirmationLink);
//            context.setVariable("from", "dormdealsshop@gmail.com");
//            context.setVariable("to", to);

            String htmlContent = templateEngine.process("confirm-email", context);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private TemplateEngine createTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }
}
