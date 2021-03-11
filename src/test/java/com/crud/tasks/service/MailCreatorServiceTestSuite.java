package com.crud.tasks.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailCreatorServiceTestSuite {

    @Autowired
    MailCreatorService mailCreatorService;

    @Test
    void shouldCreateTrelloCardMail() {
        //Given
        String message = "testMessage";
        //When
        String result = mailCreatorService.buildTrelloCardEmail(message);
        //Then
        assertNotNull(result);
    }

    @Test
    void shouldCreateOnceADayInformationMail() {
        //Given
        String message = "testMessage";
        //When
        String result = mailCreatorService.buildTasksNumberInformationEmail(message);
        //Then
        assertNotNull(result);
    }


}