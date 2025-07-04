package com.example.tasktracker.service;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTask() {
        Task task = new Task();
        task.setDescription("Go jogging");

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.addTask(task);
        assertEquals("Go jogging", result.getDescription());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task();
        task1.setDescription("Task 1");

        Task task2 = new Task();
        task2.setDescription("Task 2");

        List<Task> mockTasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Sample Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTask(1L);
        assertTrue(result.isPresent());
        assertEquals("Sample Task", result.get().getDescription());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testMarkCompleted() {
        Task task = new Task();
        task.setId(2L);
        task.setDescription("Buy milk");
        task.setCompleted(false);

        when(taskRepository.findById(2L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Optional<Task> result = taskService.markCompleted(2L);
        assertTrue(result.isPresent());
        assertTrue(result.get().isCompleted());
        verify(taskRepository, times(1)).findById(2L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteTask() {
        Long id = 3L;

        doNothing().when(taskRepository).deleteById(id);

        taskService.deleteTask(id);
        verify(taskRepository, times(1)).deleteById(id);
    }
}
