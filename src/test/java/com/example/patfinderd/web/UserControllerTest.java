package com.example.patfinderd.web;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.patfinderd.model.entity.User;
import com.example.patfinderd.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  void testOpenRegisterForm() throws Exception {
    mockMvc.
        perform(get("/users/register"))
        .andExpect(status().isOk())
        .andExpect(view().name("register"));
  }

  private static final String TEST_USER_EMAIL = "pesho@example.com";
  private static final int TEST_USER_AGE = 12;

  @Test
  void testRegisterUser() throws Exception {
    mockMvc.perform(post("/users/register").
        param("username",TEST_USER_EMAIL).
        param("fullName","Pesho Petrov").
        param("email",TEST_USER_EMAIL).
        param("age",String.valueOf(TEST_USER_AGE)).
        param("password","12345").
        param("confirmPassword","12345").
        with(csrf()).
        contentType(MediaType.APPLICATION_FORM_URLENCODED)
    ).
        andExpect(status().is3xxRedirection());

    Assertions.assertEquals(1, userRepository.count());

    Optional<User> newlyCreatedUserOpt = userRepository.findByEmail(TEST_USER_EMAIL);

    Assertions.assertTrue(newlyCreatedUserOpt.isPresent());

    User newlyCreatedUser = newlyCreatedUserOpt.get();

    Assertions.assertEquals(TEST_USER_AGE, newlyCreatedUser.getAge());
    // todo - check the remaining properties
  }

}
