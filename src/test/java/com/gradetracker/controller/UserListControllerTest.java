package com.gradetracker.controller;

import com.gradetracker.model.User;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing List of Users. The test do not use mock data, so they are dependent on the
 * correct setup of the database
 *
 * @author Olga Bradford
 * @since 4/23/2026
 */
class UserListControllerTest {
  private UserListController controller;

  @BeforeEach
  void setUp() {
    controller = new UserListController();
  }

  @Test
  void getUserData() {
    ObservableList<User> users = controller.getUserData();
    assertNotNull(users, "Should not be null. Must have at least 1 user by default");
  }

  @Test
  void testUserListNotEmpty() {
    ObservableList<User> users = controller.getUserData();
    assertFalse(users.isEmpty(), "Should not be empty. Must have at least 1 user by default");
  }

  @Test
  void testValidFields() {
    ObservableList<User> users = controller.getUserData();

    for (User user : users) {
      assertTrue(user.getUserId() > 0, "Must have positive userId");
      assertNotNull(user.getUserName(), "Must have a username");
      assertNotNull(user.getRoleName(), "roleName should not be null");
    }
  }
}
