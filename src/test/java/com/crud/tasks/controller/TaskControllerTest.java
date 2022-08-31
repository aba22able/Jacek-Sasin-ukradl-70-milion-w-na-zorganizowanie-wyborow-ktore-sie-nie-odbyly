package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldFetchEmptyTaskBoards() throws Exception {
        // Given
        List<Task> tasks = new ArrayList<>();
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTaskBoards() throws Exception {
        // Given
        List<Task> tasks = List.of(new Task(1L, "Title1", "Content1"), new Task(2L, "Title2", "Content2"), new Task(3L, "Title3", "Content3"));
        when(service.getAllTasks()).thenReturn(tasks);

        List<TaskDto> tasksDto = List.of(new TaskDto(1L, "Title1", "Content1"), new TaskDto(2L, "Title2", "Content2"), new TaskDto(3L, "Title3", "Content3"));
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Title2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].content", Matchers.is("Content3")));
    }

    @Test
    void shouldFetchTask() throws Exception {
        // Given
        Task sampleTask = new Task(1L, "sampleTaskTitle", "sampleTask content");
        TaskDto sampleTaskDto = new TaskDto(1L, "sampleTaskTitle", "sampleTask content");

        when(service.getTask(any())).thenReturn(sampleTask);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(sampleTaskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/" + "1")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("sampleTaskTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("sampleTask content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // Given
        Task sampleTask = new Task(1L, "sampleTaskTitle", "sampleTask content");
        when(service.getTask(any(long.class))).thenReturn(sampleTask);
        doNothing().when(service).deleteTask(any(Task.class));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks" + "/1"))
                .andExpect(MockMvcResultMatchers.status().is(200)); // or isOk();
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        Task sampleTask = new Task(1L, "sampleTaskTitle", "sampleTask content");
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(sampleTask);
        when(service.saveTask(any(Task.class))).thenReturn(sampleTask);

        TaskDto sampleTaskDto = new TaskDto(1L, "sampleTaskTitle", "sampleTask content");
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(sampleTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(sampleTask);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("sampleTaskTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("sampleTask content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Given
        Task sampleTask = new Task(1L, "sampleTaskTitle", "sampleTask content");
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(sampleTask);
        when(service.saveTask(any(Task.class))).thenReturn(sampleTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(sampleTask);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}