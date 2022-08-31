package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @InjectMocks
    DbService dbService;

    @Mock
    TaskRepository repository;

    @Test
    void getAllTasks() {
        //Given
        Task task1 = new Task(1L,"Task1", "Basic Content");
        Task task2 = new Task(2L,"Task2", "Basic Content");
        Task task3 = new Task(3L,"Task3", "Basic Content");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        when(repository.findAll()).thenReturn(taskList);
        //When
        List<Task> list = dbService.getAllTasks();

        //Them
        assertEquals(3, list.size());
        assertEquals(2L, list.get(1).getId());
    }

    @Test
    void getTask() throws TaskNotFoundException {
        //Given
        Task task1 = new Task(1L,"Task1", "Basic Content");

        when(repository.findById(1L)).thenReturn(Optional.of(task1));

        //When
        Task foundTask = dbService.getTask(1L);

        //Then
        assertEquals("Task1", foundTask.getTitle());
        assertEquals("Basic Content", foundTask.getContent());


    }
}