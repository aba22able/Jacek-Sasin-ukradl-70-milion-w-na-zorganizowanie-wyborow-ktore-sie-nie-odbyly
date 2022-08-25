package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void mapToBoards() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "list1", true);
        List<TrelloListDto> list1 = new ArrayList<>();
        list1.add(trelloListDto1);

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "board1", list1);

        TrelloListDto trelloListDto2 = new TrelloListDto("1", "list2", false);
        List<TrelloListDto> list2 = new ArrayList<>();
        list1.add(trelloListDto2);

        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "board2", list2);

        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto1);
        trelloBoardDtoList.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        System.out.println("TrelloBoards size = " + trelloBoards.size());
        assertEquals(2, trelloBoards.size());
    }

    @Test
    void mapToBoardsDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "list1", true);
        List<TrelloList> list1 = new ArrayList<>();
        list1.add(trelloList1);

        TrelloBoard trelloBoard1 = new TrelloBoard("1", "board1", list1);

        TrelloList trelloList2 = new TrelloList("1", "list1", true);
        List<TrelloList> list2 = new ArrayList<>();
        list1.add(trelloList2);

        TrelloBoard trelloBoard2 = new TrelloBoard("2", "board2", list2);

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        System.out.println("TrelloBoardsDto size = " + trelloBoardsDto.size());
        assertEquals(2, trelloBoardsDto.size());
    }

    @Test
    void mapToListDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "list1", true);
        TrelloList trelloList2 = new TrelloList("2", "list2", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);

        //When
        List<TrelloListDto> trelloListDtoList = trelloMapper.mapToListDto(trelloLists);

        //Then
        System.out.println("TrelloListDtoList size = " + trelloListDtoList.size());
        assertEquals(2, trelloListDtoList.size());
    }

    @Test
    void mapToList() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "list1", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "list2", false);
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(trelloListDto1);
        trelloListsDto.add(trelloListDto2);

        //When
        List<TrelloList> trelloListList = trelloMapper.mapToList(trelloListsDto);

        //Then
        System.out.println("TrelloListList size = " + trelloListList.size());
        assertEquals(2, trelloListList.size());
    }

    @Test
    void mapToCardDto() {
        //Given
        TrelloCard trelloCard1 = new TrelloCard("Card1", "Sample description", "1", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard1);

        //Then
        System.out.println("Name of card after mapping is- " + trelloCardDto.getName());
        assertEquals("Card1", trelloCardDto.getName());
    }

    @Test
    void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto1 = new TrelloCardDto("Card1", "Sample description", "1", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto1);

        //Then
        System.out.println("ListId of card after mapping is= " + trelloCard.getListId());
        assertEquals( "1",trelloCard.getListId());
    }
}