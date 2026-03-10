package com.example.taskapp.controller;

import com.example.taskapp.entity.Task;
import com.example.taskapp.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("未認証で /tasks にアクセスするとログイン画面へリダイレクトされる")
    void unauthenticatedAccessShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    @DisplayName("認証済みで /tasks にアクセスすると200で一覧画面が表示される")
    void authenticatedAccessShouldReturnTasksPage() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/index"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    @DisplayName("POST /api/tasks でタスク登録に成功する")
    void createTaskViaApiShouldSucceed() throws Exception {
        String json = """
                {
                  "title": "API test task",
                  "completed": false
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("API test task")))
                .andExpect(jsonPath("$.completed", is(false)));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    @DisplayName("存在しないIDのAPIアクセスで404が返る")
    void notFoundApiShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/tasks/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    @DisplayName("存在しないIDの画面アクセスで404画面が返る")
    void notFoundViewShouldReturn404Page() throws Exception {
        mockMvc.perform(get("/tasks/9999/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }
}