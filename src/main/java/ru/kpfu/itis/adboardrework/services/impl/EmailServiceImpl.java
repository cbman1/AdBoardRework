package ru.kpfu.itis.adboardrework.services.impl;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final UserRepository userRepository;
    private static final String API_KEY = "26719FC003A4F55B48C00108E095653A8266780841DE71D1512DF508B788C287F27885E286E8C3B24FC299DD42941B08";
    private static final String API_URL = "https://api.elasticemail.com/v2/";


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
    public void sendEmail(String to, String subject, String confirmationLink) throws IOException {
        OkHttpClient client = new OkHttpClient();
//        String htmlContent = templateEngine.process("confirm-email");
        String htmlTemplate = readHtmlFile("C:\\Users\\79174\\IdeaProjects\\AdBoardRework\\src\\main\\resources\\templates\\confirm-email.html");
        String htmlContent = htmlTemplate.replace("{{confirmationLink}}", confirmationLink);
        RequestBody requestBody = new FormBody.Builder()
                .add("apikey", API_KEY)
                .add("subject", subject)
                .add("from", "ivlev1993@gmail.com")
                .add("fromName", "AdBoard")
                .add("to", to)
                .add("confirmationLink", confirmationLink)
                .add("bodyHtml", htmlContent)
                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        Request request = new Request.Builder()
                .url(API_URL + "email/send")
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                assert response.body() != null;
                throw new Exception("Failed to send email: " + response.body().string());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String readHtmlFile(String filePath) throws IOException {
        byte[] encodedBytes = Files.readAllBytes(Path.of(filePath));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
//            Context context = new Context();
//            context.setVariable("confirmationLink", confirmationLink);
////            context.setVariable("from", "dormdealsshop@gmail.com");
////            context.setVariable("to", to);
//
//            String htmlContent = templateEngine.process("confirm-email", context);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setFrom(from);
//            mimeMessageHelper.setText(htmlContent, true);
//
//            mailSender.send(message);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
}

//    private TemplateEngine createTemplateEngine() {
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setPrefix("templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML");
//
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver);
//
//        return templateEngine;
//    }

