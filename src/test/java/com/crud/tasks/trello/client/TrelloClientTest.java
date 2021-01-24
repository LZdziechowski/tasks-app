package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Test
    void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("testKey");
        when(trelloConfig.getTrelloAppToken()).thenReturn("testToken");
        when(trelloConfig.getTrelloAppUsername()).thenReturn("testUsername");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI url = new URI("http://test.com/members/testUsername/boards?key=testKey&token=testToken&fields=name,id&lists=all");

        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    void shouldReturnEmptyList() throws URISyntaxException {
        //Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("testKey");
        when(trelloConfig.getTrelloAppToken()).thenReturn("testToken");
        when(trelloConfig.getTrelloAppUsername()).thenReturn("testUsername");

        URI url = new URI("http://test.com/members/testUsername/boards?key=testKey&token=testToken&fields=name,id&lists=all");

        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(null);
        //When
        List<TrelloBoardDto> resultTrelloBoards = trelloClient.getTrelloBoards();
        //Then
        assertEquals(0, resultTrelloBoards.size());
    }

    @Test
    void shouldCreateCard() throws URISyntaxException {
        //Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("testKey");
        when(trelloConfig.getTrelloAppToken()).thenReturn("testToken");
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test description",
                "top",
                "test_id"
        );
        Badges testBadges = new Badges(1, new AttachmentsByType(new Trello(1, 1)));
        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                testBadges,
                "test_name",
                "http://test.com/shortUrl"
        );
        URI url = new URI("http://test.com/cards?key=testKey&token=testToken&name=Test%20task&desc=Test%20description" +
                    "&pos=top&idList=test_id");
        when(restTemplate.postForObject(url, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);
        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        //Then
        assertEquals("1", newCard.getId());
        assertEquals(testBadges, newCard.getBadges());
        assertEquals("test_name", newCard.getName());
        assertEquals("http://test.com/shortUrl", newCard.getShortUrl());
    }
}