package org.example.patientapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PatientController {

    @FXML private TextField nomField;
    @FXML private TextArea consultationField;
    @FXML private ListView<Patient> patientListView;

    private ObservableList<Patient> patients;

    @FXML
    public void initialize() {
        patients = FXCollections.observableArrayList();
        patientListView.setItems(patients);
    }

    @FXML
    public void handleAddPatient() {
        String nom = nomField.getText();
        String consultation = consultationField.getText();

        if (nom.isEmpty() || consultation.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Patient patient = new Patient(nom, consultation);
        patients.add(patient);
        nomField.clear();
        consultationField.clear();
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
