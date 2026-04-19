package com.gradetracker.dao;

import com.gradetracker.model.ClassRecord;
import com.gradetracker.model.User;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Robert Mozzetti
 * created: 4/17/2026
 * Explanation: Data access interface for class creation.
 */
public interface ClassDao {

  List<User> findAvailableTeachers();

  List<User> findStudents();

  List<User> findEnrolledStudents(int classId);

  int saveClass(ClassRecord classRecord);

  void enrollStudents(int classId, List<Integer> studentIds);

  void enrollStudent(int classId, int studentId);

  boolean isStudentEnrolled(int classId, int studentId);

  ObservableList<ClassRecord> getAllClasses();
}
