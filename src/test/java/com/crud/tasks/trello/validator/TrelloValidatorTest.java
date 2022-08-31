package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloValidatorTest {

    @Autowired
    TrelloValidator trelloValidator;

    @Test
    void validateTrelloBoards() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "trelloList1", true);
        TrelloList trelloList2 = new TrelloList("2", "trelloList2", true);
        List<TrelloList> trelloLists1 = new ArrayList<>();
        trelloLists1.add(trelloList1);
        trelloLists1.add(trelloList2);
        TrelloBoard trelloBoard = new TrelloBoard("1", "TrelloBoard1", trelloLists1);

        TrelloList trelloList3 = new TrelloList("3", "trelloList3", true);
        TrelloList trelloList4 = new TrelloList("4", "trelloList4", true);
        List<TrelloList> trelloLists2 = new ArrayList<>();
        trelloLists2.add(trelloList3);
        trelloLists2.add(trelloList4);
        TrelloBoard testBoard = new TrelloBoard("2", "test", trelloLists2);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        trelloBoards.add(testBoard);

        //When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, filteredBoards.size());

    }
}