package com.dowczarek.controller;

import com.dowczarek.model.Task;
import com.dowczarek.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TaskController.class)
@ActiveProfiles("integration")
class TaskControllerLightIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository repository;

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        // given
        String description = "Foo";
        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(new Task(description, LocalDateTime.now())));

        // when + then
        mockMvc.perform(get("/tasks/123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(containsString(description)));
    }
}
