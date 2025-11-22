package com.andy.examapp;

import com.andy.examapp.models.Participant;
import com.google.gson.Gson;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ParticipantDetailsController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;

    private Participant participant;
    private final Gson gson = new Gson();

    public void setParticipant(Participant participant) {
        this.participant = participant;

        nameField.setText(participant.getName());
        emailField.setText(participant.getEmail());
        phoneField.setText(participant.getPhone());

        nameField.setEditable(false);
        phoneField.setEditable(false);
    }

    @FXML
    private void enableEditing() {
        nameField.setEditable(true);
        phoneField.setEditable(true);
    }

    @FXML
    private void saveChanges() {
        try {
            participant.setName(nameField.getText());
            participant.setPhone(phoneField.getText());

            String json = gson.toJson(participant);

            ApiClient.put("/participants/" + participant.getId(), json);

            closeModal();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeModal() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
