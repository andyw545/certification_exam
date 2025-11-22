package com.andy.examapp;

import java.lang.reflect.Type;
import java.util.List;

import com.andy.examapp.models.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CourseController {

    private final Gson gson = new Gson();

    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, Long> colId;
    @FXML private TableColumn<Course, String> colCode;
    @FXML private TableColumn<Course, String> colTitle;
    @FXML private TableColumn<Course, String> colDescription;
    @FXML private TableColumn<Course, Boolean> colStatus;
    @FXML private TableColumn<Course, Void> colToggle;

    @FXML private TextField codeField;
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;

    @FXML
    public void initialize() {
        // property names MUST match frontend Course model getters
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("active"));

        // Status text
        colStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean active, boolean empty) {
                super.updateItem(active, empty);
                if (empty || active == null) {
                    setText(null);
                } else {
                    setText(active ? "Active" : "Inactive");
                }
            }
        });

        // Toggle button column
        colToggle.setCellFactory(param -> new TableCell<>() {
            private final Button actionBtn = new Button();

            {
                actionBtn.setOnAction(event -> {
                    Course c = getTableView().getItems().get(getIndex());
                    toggleStatus(c);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Course c = getTableView().getItems().get(getIndex());
                    actionBtn.setText(c.isActive() ? "Disable" : "Activate");
                    setGraphic(actionBtn);
                }
            }
        });
    }

    @FXML
    private void loadCourses() {
        try {
            String response = ApiClient.get("/courses");
            Type listType = new TypeToken<List<Course>>(){}.getType();
            List<Course> courses = gson.fromJson(response, listType);
            coursesTable.getItems().setAll(courses);
            System.out.println("Loaded " + courses.size() + " courses.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCourse() {
        try {
            String code = codeField.getText();
            String title = titleField.getText();
            String description = descriptionField.getText();

            if (code.isBlank() || title.isBlank()) {
                System.out.println("Code and Title required.");
                return;
            }

            // matches backend JSON contract: code, title, description
            String json = String.format(
                    "{\"code\":\"%s\", \"title\":\"%s\", \"description\":\"%s\"}",
                    code, title, description
            );

            ApiClient.post("/courses", json);

            loadCourses();
            codeField.clear();
            titleField.clear();
            descriptionField.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleStatus(Course course) {
        try {
            String response = ApiClient.put("/courses/" + course.getId() + "/toggle-status", "{}");
            Course updated = gson.fromJson(response, Course.class);
            course.setActive(updated.isActive());
            coursesTable.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDetailsModal() {
        Course selected = coursesTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("No course selected.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course_details.fxml"));
            Parent root = loader.load();

            CourseDetailsController controller = loader.getController();
            controller.setCourse(selected);

            Stage stage = new Stage();
            stage.setTitle("Course Details");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadCourses();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
