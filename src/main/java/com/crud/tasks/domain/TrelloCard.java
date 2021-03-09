package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrelloCard {

    private final String name;
    private final String description;
    private final String pos;
    private final String listId;
}
