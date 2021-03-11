package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DbServiceTestSuite {

    @Mock
    DbService dbService;

    @Test
    void shouldReturnTasks() {
        //Given
        Task task = new Task();
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(dbService.getAllTasks()).thenReturn(taskList);
        //When
        List<Task> result = dbService.getAllTasks();
        //Then
        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnTask() {
        //Given
        long taskId = 1L;
        Optional<Task> task = Optional.of(new Task(1L, "testName", "testContent"));
        when(dbService.getTask(taskId)).thenReturn(task);
        //When
        Task result = dbService.getTask(taskId).get();
        //Then
        assertEquals(1L, result.getId());
        assertEquals("testName", result.getTitle());
        assertEquals("testContent", result.getContent());
    }

    @Test
    void shouldSaveTasks() {
        //Given
        Task task = new Task(1L, "testName", "testContent");
        when(dbService.saveTask(task)).thenReturn(task);
        //When
        Task result = dbService.saveTask(task);
        //Then
        assertEquals(1L, result.getId());
        assertEquals("testName", result.getTitle());
        assertEquals("testContent", result.getContent());
    }

}