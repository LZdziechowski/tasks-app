package com.crud.tasks.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
public class Mail {

    private final String mailTo;
    private final String toCc;
    private final String subject;
    private final String message;
}
