package com.gradetracker.controller;

import com.gradetracker.dao.ClassDao;
import com.gradetracker.dao.SqliteClassDao;
import com.gradetracker.manager.Session;
import com.gradetracker.model.ClassRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the scene with list of classes for non-admin users
 *
 * @author Olga Bradford
 * @since 4/19/2026
 */
public class NonAdminClassesController {

  @FXML
  private FlowPane cardList;

  private final ClassDao classDao = new SqliteClassDao();

  @FXML
  public void initialize() {
    getClasses();
  }

  public void getClasses() {

    int userId = Session.getUserId();
    List<ClassRecord> classes;

    if (Session.isTeacher()) {
      classes = classDao.getClassesByTeacher(userId);
    } else if(Session.isStudent()) {
      classes = classDao.getStudentClasses(userId);
    }else{
      classes = new ArrayList<>();
      System.out.println("Something went wrong. Invalid user role");
    }

    //clear the list of classes
    cardList.getChildren().clear();

    for (ClassRecord record : classes) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/class-card.fxml"));
        Parent card = loader.load();

        // passing data to the card controller
        ClassCardController controller = loader.getController();
        controller.setData(record);
        cardList.getChildren().add(card);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
