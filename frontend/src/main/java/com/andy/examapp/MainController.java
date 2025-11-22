package com.andy.examapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() {
        loadPage("participant.fxml");
    }

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node view = loader.load();

            contentArea.getChildren().setAll(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);

        } catch (Exception e) {
            System.out.println("Failed to load: " + fxmlFile);
            e.printStackTrace();
        }
    }

    @FXML
    private void showParticipants() {
        loadPage("participant.fxml");
    }

    @FXML
    private void showCourses() {
        loadPage("course.fxml");
    }

    @FXML
    private void showEnrollments() {
        loadPage("enrollment.fxml");
    }
}
