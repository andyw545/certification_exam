package com.andy.examapp;

import com.andy.examapp.models.Course;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CourseDetailsController {

    @FXML private TextField codeField;
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;

    private Course course;
    private final Gson gson = new Gson();

    public void setCourse(Course course) {
        this.course = course;
        codeField.setText(course.getCode());
        titleField.setText(course.getTitle());
        descriptionField.setText(course.getDescription());
        disableEditing(true);
    }

    @FXML
    private void enableEditing() {
        disableEditing(false);
    }

    private void disableEditing(boolean disable) {
        codeField.setDisable(disable);
        titleField.setDisable(disable);
        descriptionField.setDisable(disable);
    }

    @FXML
    private void saveChanges() {
        try {
            course.setCode(codeField.getText());
            course.setTitle(titleField.getText());
            course.setDescription(descriptionField.getText());

            String payload = gson.toJson(course);
            ApiClient.put("/courses/" + course.getId(), payload);

            disableEditing(true);
            showAlert("Saved", "Course updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update course.");
        }
    }

    @FXML
    private void closeModal() {
        Stage stage = (Stage) codeField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
