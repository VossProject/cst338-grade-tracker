package com.gradetracker.manager;

/**
 * Manages the current user session for the application.
 * Stores the logged-in user's ID and role ID so other controllers
 * can access user-specific data and behavior throughout the app.
 * Uses static fields to maintain session state during runtime.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class Session {
  private static Integer userId;
  private static Integer roleId;

  public static final int ROLE_ADMIN = 1;
  public static final int ROLE_TEACHER = 2;
  public static final int ROLE_STUDENT = 3;

  public static void startSession(int userId, int roleId) {
    Session.userId = userId;
    Session.roleId = roleId;
  }

  public static boolean isAdmin() { return Integer.valueOf(ROLE_ADMIN).equals(roleId); }
  public static boolean isTeacher() { return Integer.valueOf(ROLE_TEACHER).equals(roleId); }
  public static boolean isStudent() { return Integer.valueOf(ROLE_STUDENT).equals(roleId); }

  public static Integer getUserId() {
    return userId;
  }

  public static Integer getRoleId() {
    return roleId;
  }

  public static boolean isLoggedIn() {
    return userId != null && roleId != null;
  }

  public static void clearSession() {
    userId = null;
    roleId = null;
  }
}
