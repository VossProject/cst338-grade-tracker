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
    void testValidUserCreation() {
        String result = CreateUserController.validate(
                "newstudent1",
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
    void testDuplicateUsername() {
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

    @Test
    void testTwoUsersIdenticalPasswords() {
        String firstResult = CreateUserController.validate(
                "student1",
                "Robert",
                "Mozzetti",
                "Student",
                "samepassword123",
                "samepassword123",
                Collections.emptySet()
        );

        String secondResult = CreateUserController.validate(
                "student2",
                "Olga",
                "Bradford",
                "Student",
                "samepassword123",
                "samepassword123",
                Set.of("student1")
        );

        assertNull(firstResult);
        assertNull(secondResult);
    }
}
