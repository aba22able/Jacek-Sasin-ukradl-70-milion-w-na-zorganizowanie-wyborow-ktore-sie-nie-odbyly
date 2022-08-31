package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task1", "Task1 content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(1L, task.getId());
        assertEquals("Task1", task.getTitle());
        assertEquals("Task1 content", task.getContent());
    }

    @Test
    void mapToTaskDto() {
        //Given
        Task task = new Task(1L, "Task1", "Task1 content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(1L, taskDto.getId());
        assertEquals("Task1", taskDto.getTitle());
        assertEquals("Task1 content", taskDto.getContent());
    }

    @Test
    void mapToTaskDtoList() {
        //Given
        Task task1 = new Task(1L, "Task1", "Task1 content");
        Task task2 = new Task(2L, "Task2", "Task2 content");
        Task task3 = new Task(3L, "Task3", "Task3 content");
        Task task4 = new Task(4L, "Task4", "Task4 content");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(3L, taskDtoList.get(2).getId());
        assertEquals("Task4", taskDtoList.get(3).getTitle());
        assertEquals("Task1 content", taskDtoList.get(0).getContent());

    }
}