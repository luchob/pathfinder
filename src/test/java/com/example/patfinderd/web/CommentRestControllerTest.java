package com.example.patfinderd.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.patfinderd.model.entity.Comment;
import com.example.patfinderd.model.entity.Route;
import com.example.patfinderd.model.entity.User;
import com.example.patfinderd.repository.RouteRepository;
import com.example.patfinderd.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser("lucho")
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

  private static final String COMMENT_1 = "something";
  private static final String COMMENT_2 = "something else";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private UserRepository userRepository;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setPassword("password");
    testUser.setUsername("lucho");
    testUser.setFullName("lucho balev");

    testUser = userRepository.save(testUser);
  }

  @AfterEach
  void tearDown() {
    routeRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void testGetComments() throws Exception {
    long routeId = initRoute();

    mockMvc.perform(get("/api/" + routeId + "/comments")).
        andExpect(status().isOk()).
        andExpect(jsonPath("$", hasSize(2))).
        andExpect(jsonPath("$.[0].message", is(COMMENT_1))).
        andExpect(jsonPath("$.[1].message", is(COMMENT_2)));
  }

  private long initRoute() {
    Route testRoute = new Route();
    testRoute.setName("Testing route");

    testRoute = routeRepository.save(testRoute);

    Comment comment1 = new Comment();
    comment1.setCreated(LocalDateTime.now());
    comment1.setAuthor(testUser);
    comment1.setTextContent(COMMENT_1);
    comment1.setApproved(true);
    comment1.setRoute(testRoute);


    Comment comment2 = new Comment();
    comment2.setCreated(LocalDateTime.now());
    comment2.setAuthor(testUser);
    comment2.setTextContent(COMMENT_2);
    comment2.setApproved(true);
    comment2.setRoute(testRoute);

    testRoute.setComments(List.of(comment1, comment2));

    return routeRepository.save(testRoute).getId();
  }
}
