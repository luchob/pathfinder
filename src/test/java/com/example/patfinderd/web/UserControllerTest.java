package com.example.patfinderd.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.patfinderd.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Test
  void testOpenRegistration() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/users/register"))
        .andExpect(status().isOk())
        .andExpect(view().name("register"));
  }

  @Test
  void testRegister() throws Exception {
    mockMvc
        .perform(post("/users/register")
            .param("username", "lucho")
            .param("email", "lucho@lucho.de")
            .param("age", "23")
            .param("password", "test")
            .param("confirmPassword", "test")
            .param("fullName", "Lucho Balev")
            .with(csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)).
        andExpect(status().is3xxRedirection());

    System.out.println(userRepository.count());
  }
}
