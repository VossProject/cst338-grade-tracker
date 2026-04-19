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

  public static void startSession(int userId, int roleId) {
    Session.userId = userId;
    Session.roleId = roleId;
  }

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
