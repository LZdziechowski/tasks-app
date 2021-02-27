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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    private final Gson gson = new Gson();

    @Test
    void shouldReturnTasks() throws Exception {
        //Given
        List<Task> taskList = List.of(new Task(1L, "testTitle", "testContent"));
        List<TaskDto> taskDtoList = List.of(new TaskDto(1L, "testTitle", "testContent"));
        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void shouldReturnTask() throws Exception {
        //Given
        Long taskId = 1L;
        Task task = new Task(1L, "testTitle", "testContent");
        TaskDto taskDto = new TaskDto(1L, "testTitle", "testContent");
        when(dbService.getTask(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        String jsonContent = gson.toJson(taskId);
        //When & Them
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("taskId", String.valueOf(taskId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("testTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("testContent")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(1L, "testTitle", "testContent");
        TaskDto taskDto = new TaskDto(1L, "testTitle", "testContent");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        String jsonContent = gson.toJson(taskDto);
        //When
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/task/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
         //Then
        assertEquals(1, dbService.getAllTasks().size());

    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L, "testTitle1", "testContent1");
        Task task2 = new Task(2L, "testTitle2", "testContent2");
        TaskDto taskDto = new TaskDto(1L, "testTitle1", "testContent1");
        TaskDto taskDto2 = new TaskDto(2L, "testTitle2", "testContent2");
        when(dbService.saveTask(task)).thenReturn(task2);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task2)).thenReturn(taskDto2);
        String jsonContent = gson.toJson(taskDto2);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "testTitle", "testContent");
        TaskDto taskDto = new TaskDto(1L, "testTitle", "testContent");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        String jsonContent = gson.toJson(taskDto);
        //dbService.saveTask(task);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("taksId", String.valueOf(1L)));

    }
}