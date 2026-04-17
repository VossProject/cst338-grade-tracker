package com.gradetracker.controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Dashboard home scene with Quote of the Day and Clock widgets
 *
 * @author Olga Bradford
 * @since 4/16/2026
 */
public class HomeController {
  @FXML
  private Label quoteText;
  @FXML
  private Label quoteAuthor;
  @FXML
  private Label timeLabel;
  @FXML
  private Label dateLabel;

  public void initialize() {
    getQuote();
    getClock();
  }

  /**
   * Reading the Zen Quotes API for QUOTE OF THE DAY
   */
  private void getQuote() {
    Thread thread = new Thread(() -> {
      try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://zenquotes.io/api/today"))
            .timeout(Duration.ofSeconds(10))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        if (body != null && body.contains("\"q\":\"")) {
          String clean = body.substring(body.indexOf("{"), body.lastIndexOf("}") + 1);
          String quote = clean.split("\"q\":\"")[1].split("\",\"")[0];
          String author = clean.split("\"a\":\"")[1].split("\",\"")[0];

          Platform.runLater(() -> {
            quoteText.setText("\"" + quote + "\"");
            quoteAuthor.setText("- " + author);
          });
        }
      } catch (Exception e) {
        //In case of an error, falling back to our placeholder quote
        Platform.runLater(() -> {
          quoteText.setText("\"Work hard, nap hard.\"");
          quoteAuthor.setText("- Otterware");
        });
      }
    });
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Setting up and animating the clock with current date and time
   */
  private void getClock() {
    Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
      LocalDateTime now = LocalDateTime.now();
      timeLabel.setText(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
      dateLabel.setText(now.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd")));
    }), new KeyFrame(javafx.util.Duration.seconds(1)));

    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }
}
