package com.example.patfinderd.service.impl;

import com.example.patfinderd.model.entity.Role;
import com.example.patfinderd.model.entity.User;
import com.example.patfinderd.model.entity.enums.RoleNameEnum;
import com.example.patfinderd.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class PathfinderUserDetailsServiceTest {

  private User testUser;
  private Role adminRole, userRole;

  private PathfinderUserDetailsService serviceToTest;

  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void init() {

    //ARRANGE
    serviceToTest = new PathfinderUserDetailsService(mockUserRepository);

    adminRole = new Role();
    adminRole.setRole(RoleNameEnum.ADMIN);

    userRole = new Role();
    userRole.setRole(RoleNameEnum.USER);

    testUser = new User();
    testUser.setUsername("lucho");
    testUser.setEmail("lucho@lucho.com");
    testUser.setPassword("topsecret");
    testUser.setRoles(Set.of(adminRole, userRole));
  }

  @Test
  void testUserNotFound() {
    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> serviceToTest.loadUserByUsername("invalid_user_email@not-exist.com")
    );
  }

  @Test
  void testUserFound() {

    // Arrange
    Mockito.when(mockUserRepository.findByEmail(testUser.getEmail())).
        thenReturn(Optional.of(testUser));

    // Act
    var actual = serviceToTest.loadUserByUsername(testUser.getEmail());

    // Assert

    String expectedRoles = "ROLE_ADMIN, ROLE_USER";
    String actualRoles = actual.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
        Collectors.joining(", "));

    Assertions.assertEquals(actual.getUsername(), testUser.getEmail());
    Assertions.assertEquals(expectedRoles, actualRoles);
  }

}
