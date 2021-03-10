package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TrelloServiceTestSuite {

    @Mock
    private TrelloClient trelloClient;

    @Test
    void shouldFetchTrelloBoards() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "testName", true);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "name", trelloListDtoList);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);
        //When
        List<TrelloBoardDto> result = trelloClient.getTrelloBoards();
        //Then
        assertEquals(1, result.size());
    }

    @Test
    void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testName", "testDescription", "testPos", "testListId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", new Badges(
                1, new AttachmentsByType(new Trello(1, 1))), "test", "http://test.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto result = trelloClient.createNewCard(trelloCardDto);
        //Then
        assertEquals("1", result.getId());
    }

}