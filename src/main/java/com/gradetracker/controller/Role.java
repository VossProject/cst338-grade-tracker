package com.gradetracker.controller;

/**
 * @author Robert Mozzetti
 * created: 4/14/2026
 * Explanation: Class file for roleId and roleName
 */

public class Role {
  private final int roleId;
  private final String roleName;

  Role(int roleId, String roleName) {
    this.roleId = roleId;
    this.roleName = roleName;
  }

  int getRoleId() {
    return roleId;
  }

  @Override
  public String toString() {
    return roleName;
  }
}
