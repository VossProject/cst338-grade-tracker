package com.gradetracker.model;

/**
 * Navigation item for the main menu
 *
 * @author Olga Bradford
 * @since 4/16/2026
 */
public class NavItem {
  private String label;
  private String fxmlPath;

  public NavItem(String label, String fxmlPath) {
    this.label = label;
    this.fxmlPath = fxmlPath;
  }

  @Override
  public String toString() { return label; }

  public String getFxmlPath() { return fxmlPath; }
}
