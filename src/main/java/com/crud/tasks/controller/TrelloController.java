package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("boards")
    public void getTrelloBoards()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        try {
            Method privateMethod
                    = TrelloClient.class.getDeclaredMethod("getTrelloBoards");

            privateMethod.setAccessible(true);

            List<TrelloBoardDto> trelloBoards = privateMethod.invoke(trelloClient.getTrelloBoards());

            trelloBoards.stream()
                    .filter(name -> name.getName() != null)
                    .filter(id -> id.getId() != null)
                    .filter(n -> n.getName().contains("Kodilla"))
                    .forEach(trelloBoardDto -> {
                        System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                    });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}