package ru.kpfu.itis.adboardrework.services;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void confirmAccount(String hashForConfirm);
    void sendEmail(String to, String subject, String confirmationLink) throws IOException;
}
