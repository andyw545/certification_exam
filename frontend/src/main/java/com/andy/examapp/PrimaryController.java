package com.andy.examapp;

import java.lang.reflect.Type;
import java.util.List;

import com.andy.examapp.models.Participant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PrimaryController {

    private final Gson gson = new Gson();

    @FXML
    private TableView<Participant> participantsTable;
    @FXML
    private TableColumn<Participant, Long> colId;
    @FXML
    private TableColumn<Participant, String> colName;
    @FXML
    private TableColumn<Participant, String> colEmail;
    @FXML
    private TableColumn<Participant, String> colPhone;

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    @FXML
    private void loadParticipants() {
        try {
            String response = ApiClient.get("/participants");

            Type listType = new TypeToken<List<Participant>>() {
            }.getType();
            List<Participant> participants = gson.fromJson(response, listType);

            participantsTable.getItems().setAll(participants);

            System.out.println("Loaded " + participants.size() + " participants.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addParticipant() {
        try {

            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (name.isBlank() || email.isBlank()) {
                System.out.println("Name and Email are required.");
                return;
            }

            // Create JSON manually
            String json = String.format(
                    "{\"name\":\"%s\", \"email\":\"%s\", \"phone\":\"%s\"}",
                    name, email, phone
            );

            ApiClient.post("/participants", json);

            // Refresh table
            loadParticipants();

            // Clear form
            nameField.clear();
            emailField.clear();
            phoneField.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDetailsModal() {
        Participant selected = participantsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("No participant selected.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("participant_detail.fxml"));
            Parent root = loader.load();

            ParticipantDetailsController controller = loader.getController();
            controller.setParticipant(selected);

            Stage stage = new Stage();
            stage.setTitle("Participant Details");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh after closing
            loadParticipants();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
