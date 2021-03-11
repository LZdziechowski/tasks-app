package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminConfigTestSuite {

    @Autowired
    AdminConfig adminConfig;

    @Test
    void shouldReturnAdminName() {
        //Given
        String name;
        //When
        name = adminConfig.getAdminName();
        //Then
        assertEquals("Lukasz", name);
    }

    @Test
    void shouldReturnAdminEmail() {
        //Given
        String email;
        //When
        email = adminConfig.getAdminMail();
        //Then
        assertEquals("testowekontozdz@gmail.com", email);
    }

}