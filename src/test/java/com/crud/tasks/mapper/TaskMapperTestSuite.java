package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTestSuite {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void shouldMapToTaskDto() {
        //Given
        Task task = new Task(1L,"testTitle","testContent");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals("testTitle", taskDto.getTitle());
        assertEquals("testContent", taskDto.getContent());
    }

    @Test
    void shouldMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L,"testTitle","testContent");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals("testTitle", task.getTitle());
        assertEquals("testContent", task.getContent());
    }

    @Test
    void shouldMapToTaskDtoList() {
        //Given
        Task task = new Task(1L,"testTitle","testContent");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(1, taskDtoList.size());
        assertEquals("testTitle", taskDtoList.get(0).getTitle());
        assertEquals("testContent", taskDtoList.get(0).getContent());
    }
}