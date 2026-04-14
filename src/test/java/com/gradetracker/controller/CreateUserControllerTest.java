package com.gradetracker.controller;

import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for CreateUserController validation.
 */
class CreateUserControllerTest {

    @Test
    void validUserReturnsNull() {
        String result = CreateUserController.validate(
                "robert1",
                "Robert",
                "Mozzetti",
                "Student",
                "password123",
                "password123",
                Collections.emptySet()
        );

        assertNull(result);
    }

    @Test
    void blankUsernameReturnsError() {
        String result = CreateUserController.validate(
                "",
                "Robert",
                "Mozzetti",
                "Student",
                "password123",
                "password123",
                Collections.emptySet()
        );

        assertEquals("Username is required.", result);
    }

    @Test
    void blankRoleReturnsError() {
        String result = CreateUserController.validate(
                "robert1",
                "Robert",
                "Mozzetti",
                "",
                "password123",
                "password123",
                Collections.emptySet()
        );

        assertEquals("Role is required.", result);
    }

    @Test
    void mismatchedPasswordsReturnError() {
        String result = CreateUserController.validate(
                "robert1",
                "Robert",
                "Mozzetti",
                "Student",
                "password123",
                "different123",
                Collections.emptySet()
        );

        assertEquals("Passwords do not match.", result);
    }

    @Test
    void duplicateUsernameReturnsError() {
        String result = CreateUserController.validate(
                "admin",
                "Robert",
                "Mozzetti",
                "Admin",
                "password123",
                "password123",
                Set.of("admin", "teacher1")
        );

        assertEquals("That username is already taken.", result);
    }
}
