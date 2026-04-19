package com.gradetracker.manager;

/**
 *
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
