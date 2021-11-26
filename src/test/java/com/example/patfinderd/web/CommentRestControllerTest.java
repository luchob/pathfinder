package com.example.patfinderd.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.patfinderd.model.binding.NewCommentBindingModel;
import com.example.patfinderd.model.entity.Comment;
import com.example.patfinderd.model.entity.Route;
import com.example.patfinderd.model.entity.User;
import com.example.patfinderd.repository.RouteRepository;
import com.example.patfinderd.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser("lucho@example.com")
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

  private static final String COMMENT_1 = "hey Spring is cool!";
  private static final String COMMENT_2 = "Well... it is a bit trick sometimes... :(";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setPassword("password");
    testUser.setUsername("lucho");
    testUser.setEmail("lucho@example.com");
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
    var route = initComments(initRoute());

    mockMvc.perform(get("/api/" + route.getId() + "/comments")).
        andExpect(status().isOk()).
        andExpect(jsonPath("$", hasSize(2))).
        andExpect(jsonPath("$.[0].message", is(COMMENT_1))).
        andExpect(jsonPath("$.[1].message", is(COMMENT_2)));
  }

  @Test
  void testCreateComments() throws Exception {
    NewCommentBindingModel testComment = new NewCommentBindingModel().
        setMessage(COMMENT_1);

    var emptyRoute = initRoute();

    mockMvc.perform(
        post("/api/" + emptyRoute.getId() + "/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testComment))
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf())
    )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/" + emptyRoute.getId() + "/comments/\\d")))
            .andExpect(jsonPath("$.message").value(is(COMMENT_1)));

  }

  private Route initRoute() {
    Route testRoute = new Route();
    testRoute.setName("Testing route");

    return routeRepository.save(testRoute);
  }

  private Route initComments(Route route) {

    Comment comment1 = new Comment();
    comment1.setCreated(LocalDateTime.now());
    comment1.setAuthor(testUser);
    comment1.setTextContent(COMMENT_1);
    comment1.setApproved(true);
    comment1.setRoute(route);

    Comment comment2 = new Comment();
    comment2.setCreated(LocalDateTime.now());
    comment2.setAuthor(testUser);
    comment2.setTextContent(COMMENT_2);
    comment2.setApproved(true);
    comment2.setRoute(route);

    route.setComments(List.of(comment1, comment2));

    return routeRepository.save(route);
  }
}
