package com.example.patfinderd.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.patfinderd.model.entity.Comment;
import com.example.patfinderd.model.entity.Route;
import com.example.patfinderd.model.entity.User;
import com.example.patfinderd.repository.CommentRepository;
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


@WithMockUser(
    username = "pesho",
    roles = {"ADMIN"}
)
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private UserRepository userRepository;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User();
    testUser.setUsername("pesho");
    testUser.setFullName("Petar Petrov");
    testUser.setPassword("test");

    testUser = userRepository.save(testUser);
  }

  @AfterEach
  void tearDown() {
    routeRepository.deleteAll();
    userRepository.deleteAll();

  }

  @Test
  void testGetComments() throws Exception {

    long routeId = setupComments();

    mockMvc.perform(get("/api/" + routeId + "/comments")).
        andExpect(status().isOk()).
        andExpect(jsonPath("$", hasSize(2))).
        andExpect(jsonPath("$.[0].message", is("No comment"))).
        andExpect(jsonPath("$.[1].message", is("No comment 2")));
  }

  private long setupComments() {
    Route route = new Route();
    route.setName("Route to heaven");

    route = routeRepository.save(route);

    Comment comment1 = new Comment();
    comment1.setCreated(LocalDateTime.now());
    comment1.setAuthor(testUser);
    comment1.setTextContent("No comment");
    comment1.setApproved(true);

    comment1.setRoute(route);

    Comment comment2 = new Comment();
    comment2.setCreated(LocalDateTime.now());
    comment2.setAuthor(testUser);
    comment2.setTextContent("No comment 2");
    comment2.setApproved(true);

    comment2.setRoute(route);

    route.setComments(List.of(comment1, comment2));

    route = routeRepository.save(route);

    System.out.println("COMMENTS " + commentRepository.count());

    return route.getId();
  }

}
