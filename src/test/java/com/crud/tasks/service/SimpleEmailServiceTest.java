package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void shouldSendEmailWithCc() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test1@test.com")
                .toCc("test2@test.com")
                .subject("testSubject")
                .message("testMessage")
                .build();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setCc(mail.getToCc());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        //When
        simpleEmailService.sendSimpleMail(mail);
        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    void shouldSendEmailWithoutCc() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test1@test.com")
                .subject("testSubject")
                .message("testMessage")
                .build();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        //When
        simpleEmailService.sendSimpleMail(mail);
        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    void shouldSendEmailWithTrelloCardTemplate() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test1@test.com")
                .subject("testSubject")
                .message("testMessage")
                .build();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        //When
        simpleEmailService.sendSimpleMail(mail);
        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }
}