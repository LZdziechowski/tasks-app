package com.crud.tasks.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailCreatorServiceTestSuite {

    MailCreatorService mailCreatorService = new MailCreatorService();

    @Test
    void shouldCreateTrelloCardMail() {
        //Given
        String message = "testMessage";
        //When
        String result = mailCreatorService.buildTrelloCardEmail(message);
        //Then
        assertNotNull(result);
    }

}