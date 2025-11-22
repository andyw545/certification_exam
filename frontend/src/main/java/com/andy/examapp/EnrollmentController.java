package com.andy.examapp;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.andy.examapp.models.Course;
import com.andy.examapp.models.Enrollment;
import com.andy.examapp.models.EnrollmentRequest;
import com.andy.examapp.models.Participant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EnrollmentController {

    @FXML
    private ComboBox<Participant> participantBox;
    @FXML
    private ListView<Course> courseList;
    @FXML
    private ComboBox<String> statusBox;

    @FXML
    private TableView<Enrollment> enrollmentTable;
    @FXML
    private TableColumn<Enrollment, String> colId;
    @FXML
    private TableColumn<Enrollment, String> colParticipant;
    @FXML
    private TableColumn<Enrollment, String> colCourse;
    @FXML
    private TableColumn<Enrollment, String> colStatus;
    @FXML
    private TableColumn<Enrollment, String> colDate;

    private final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URL = "http://localhost:8080/api";

    @FXML
    public void initialize() {
        statusBox.getItems().addAll("ENROLLED", "WITHDRAWN", "COMPLETED");

        courseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setupTable();
        loadParticipants();
        loadCourses();
        loadEnrollments();
    }

    private void setupTable() {
        colId.setCellValueFactory(param
                -> new SimpleStringProperty(param.getValue().getId().toString())
        );
        colParticipant.setCellValueFactory(param
                -> new SimpleStringProperty(param.getValue().getParticipant().getName())
        );
        colCourse.setCellValueFactory(param
                -> new SimpleStringProperty(param.getValue().getCourse().getTitle())
        );
        colStatus.setCellValueFactory(param
                -> new SimpleStringProperty(param.getValue().getStatus())
        );
        colDate.setCellValueFactory(param
                -> new SimpleStringProperty(param.getValue().getEnrolledAt())
        );
    }

    private void loadParticipants() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/participants"))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Type type = new TypeToken<List<Participant>>() {
            }.getType();
            List<Participant> participants = gson.fromJson(response.body(), type);

            participantBox.getItems().setAll(participants);

        } catch (Exception e) {
            showAlert("Error loading participants: " + e.getMessage());
        }
    }

    private void loadCourses() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/courses"))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Type type = new TypeToken<List<Course>>() {
            }.getType();
            List<Course> courses = gson.fromJson(response.body(), type);

            courseList.getItems().setAll(courses);

        } catch (Exception e) {
            showAlert("Error loading courses: " + e.getMessage());
        }
    }

    private void loadEnrollments() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/enrollments"))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Type type = new TypeToken<List<Enrollment>>() {
            }.getType();
            List<Enrollment> enrollments = gson.fromJson(response.body(), type);

            enrollmentTable.getItems().setAll(enrollments);

        } catch (Exception e) {
            showAlert("Error loading enrollments: " + e.getMessage());
        }
    }

    @FXML
    private void handleEnroll() {
        Participant selectedParticipant = participantBox.getValue();
        String selectedStatus = statusBox.getValue();

        List<Long> selectedCourseIds = courseList.getSelectionModel()
                .getSelectedItems()
                .stream()
                .map(Course::getId)
                .collect(java.util.stream.Collectors.toList());

        if (selectedParticipant == null || selectedStatus == null || selectedCourseIds.isEmpty()) {
            showAlert("Please choose participant, status, and at least one course.");
            return;
        }

        try {
            EnrollmentRequest requestBody = new EnrollmentRequest(
                    selectedParticipant.getId(),
                    selectedCourseIds,
                    selectedStatus
            );

            String json = gson.toJson(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/enrollments"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                showAlert("Enrollment successful!");
                loadEnrollments();
                clearFields();
            } else {
                showAlert("Failed to enroll: " + response.body());
            }

        } catch (Exception e) {
            showAlert("Error enrolling: " + e.getMessage());
        }
    }

    private void clearFields() {
        participantBox.setValue(null);
        statusBox.setValue(null);
        courseList.getSelectionModel().clearSelection();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.show();
    }
}
