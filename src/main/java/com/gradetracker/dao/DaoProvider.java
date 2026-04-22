package com.gradetracker.dao;

/**
 * Central place to choose DAO implementations.
 *
 * <p>Default behavior is to use in-memory implementations so the app can be run
 * locally without creating/depending on a SQLite DB for login.
 *
 * <p>To force SQLite for users, run with: {@code -Dgradetracker.userDao=sqlite}
 */
public final class DaoProvider {

  private static volatile UserDao userDao;

  private DaoProvider() {}

  /**
   * Returns the UserDao implementation for this run.
   *
   * <p>This is cached so multiple controllers share the same instance (useful
   * for in-memory testing where {@code save()} should affect other screens).
   */
  public static UserDao userDao() {
    UserDao local = userDao;
    if (local != null) {
      return local;
    }
    synchronized (DaoProvider.class) {
      if (userDao == null) {
        userDao = buildUserDao();
      }
      return userDao;
    }
  }

  private static UserDao buildUserDao() {
    String selection = System.getProperty("gradetracker.userDao", "inmemory").trim();
    if ("sqlite".equalsIgnoreCase(selection) || "db".equalsIgnoreCase(selection)) {
      return new SqliteUserDao();
    }
    return new SqliteUserDao();
  }
}

