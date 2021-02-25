package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {

    public TrelloBoard mapToBoard(final TrelloBoardDto trelloBoardDto) {
        return new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToList(trelloBoardDto.getLists()));
    }

    public TrelloBoardDto mapToBoardDto(final TrelloBoard trelloBoard) {
        return new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(), mapToListDto(trelloBoard.getLists()));
    }

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDtoList) {
        return trelloBoardDtoList.stream()
                .map(this::mapToBoard)
                .collect(Collectors.toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoardList) {
        return trelloBoardList.stream()
                .map(this::mapToBoardDto)
                .collect(Collectors.toList());
    }

    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDto) {
        return trelloListDto.stream()
                .map(trelloList -> new TrelloList(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(Collectors.toList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(trelloList -> new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(Collectors.toList());
    }

    public TrelloCard mapToCard(TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(), trelloCardDto.getListId());
    }

    public TrelloCardDto mapToCardDto(TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(), trelloCard.getListId());
    }
}
