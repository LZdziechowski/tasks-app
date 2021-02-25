package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTestSuite {


    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    void testMapTo() {
        //Given
        TrelloList trelloListTest = new TrelloList("testId", "testName", true);
        List<TrelloList> trelloListListTest = new ArrayList<>();
        trelloListListTest.add(trelloListTest);
        TrelloBoard trelloBoardTest = new TrelloBoard("testId", "testName", trelloListListTest);
        TrelloListDto trelloListDtoTest = new TrelloListDto("testIdDto", "testNameDto", false);
        List<TrelloListDto> trelloListDtosTest = new ArrayList<>();
        trelloListDtosTest.add(trelloListDtoTest);
        TrelloBoardDto trelloBoardDtoTest = new TrelloBoardDto("testIdDto", "testNameDto", trelloListDtosTest);
        List<TrelloBoard> trelloBoardsTest = new ArrayList<>();
        trelloBoardsTest.add(trelloBoardTest);
        List<TrelloBoardDto> trelloBoardDtosTest = new ArrayList<>();
        trelloBoardDtosTest.add(trelloBoardDtoTest);
        TrelloCard trelloCardTest = new TrelloCard("testName", "testDescription", "testPos", "testListId");
        TrelloCardDto trelloCardDtoTest = new TrelloCardDto("testNameDto", "testDescriptionDto", "testPosDto", "testListIdDto");
        //When & Then
        assertAll(
                () -> assertTrue(trelloMapper.mapToBoard(trelloBoardDtoTest) instanceof TrelloBoard),
                () -> assertTrue(trelloMapper.mapToBoardDto(trelloBoardTest) instanceof TrelloBoardDto),
                () -> assertTrue(trelloMapper.mapToBoards(trelloBoardDtosTest) instanceof List),
                () -> assertTrue(trelloMapper.mapToBoardsDto(trelloBoardsTest) instanceof List),
                () -> assertTrue(trelloMapper.mapToList(trelloListDtosTest) instanceof List),
                () -> assertTrue(trelloMapper.mapToListDto(trelloListListTest) instanceof List),
                () -> assertTrue(trelloMapper.mapToCard(trelloCardDtoTest) instanceof TrelloCard),
                () -> assertTrue(trelloMapper.mapToCardDto(trelloCardTest) instanceof TrelloCardDto),
                () -> assertEquals("testIdDto", trelloMapper.mapToBoard(trelloBoardDtoTest).getId()),
                () -> assertEquals("testName", trelloMapper.mapToBoardDto(trelloBoardTest).getName()),
                () -> assertNotNull(trelloMapper.mapToBoards(trelloBoardDtosTest)),
                () -> assertNotNull(trelloMapper.mapToBoardsDto(trelloBoardsTest)),
                () -> assertNotNull(trelloMapper.mapToList(trelloListDtosTest)),
                () -> assertNotNull(trelloMapper.mapToListDto(trelloListListTest)),
                () -> assertNotNull(trelloMapper.mapToCard(trelloCardDtoTest)),
                () -> assertNotNull(trelloMapper.mapToCardDto(trelloCardTest))
        );
    }
}