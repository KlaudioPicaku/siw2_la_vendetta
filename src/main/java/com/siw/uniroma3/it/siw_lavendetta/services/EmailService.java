package com.siw.uniroma3.it.siw_lavendetta.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String to, String subject, String text) throws MessagingException;

    void sendPasswordResetEmail(String to, String subject, String text) throws  MessagingException;
}
