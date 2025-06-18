// PatientController.java
package org.example.patientapp;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class PatientController {

    @FXML private TextField nomField, cinField, consultationField, prixField, searchField;
    @FXML private Label alertLabel;
    @FXML private Button addOrUpdateButton;
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> colNom, colCin, colConsultation;
    @FXML private TableColumn<Patient, Double> colPrix;
    @FXML private TableColumn<Patient, Void> colAction;

    private final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private FilteredList<Patient> filteredPatients;
    private Patient selectedPatient = null;

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colConsultation.setCellValueFactory(new PropertyValueFactory<>("consultation"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        filteredPatients = new FilteredList<>(patients, p -> true);
        patientTable.setItems(filteredPatients);

        initActionColumn();
        setupSearch();

        patientTable.setRowFactory(tv -> {
            TableRow<Patient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    selectedPatient = row.getItem();
                    nomField.setText(selectedPatient.getNom());
                    cinField.setText(selectedPatient.getCin());
                    consultationField.setText(selectedPatient.getConsultation());
                    prixField.setText(String.valueOf(selectedPatient.getPrix()));
                    addOrUpdateButton.setText("✏️ Modifier");
                }
            });
            return row;
        });
    }

    private void setupSearch() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredPatients.setPredicate(patient ->
                    patient.getNom().toLowerCase().contains(newVal.toLowerCase()) ||
                            patient.getCin().toLowerCase().contains(newVal.toLowerCase())
            );
        });
    }

    private void initActionColumn() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("supprimer");
            private final Button editBtn = new Button("modifier");

            {
                deleteBtn.getStyleClass().add("delete-button");
                editBtn.getStyleClass().add("edit-button");

                deleteBtn.setOnAction(e -> {
                    Patient p = getTableView().getItems().get(getIndex());
                    patients.remove(p);
                    showAlert("🗑️ Patient supprimé.", "success");
                });

                editBtn.setOnAction(e -> {
                    Patient p = getTableView().getItems().get(getIndex());
                    selectedPatient = p;
                    nomField.setText(p.getNom());
                    cinField.setText(p.getCin());
                    consultationField.setText(p.getConsultation());
                    prixField.setText(String.valueOf(p.getPrix()));
                    addOrUpdateButton.setText("✏️ Modifier");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, editBtn, deleteBtn));
                }
            }
        });
    }

    @FXML
    public void handleAddOrUpdatePatient() {
        String nom = nomField.getText().trim();
        String cin = cinField.getText().trim();
        String consultation = consultationField.getText().trim();
        String prixText = prixField.getText().trim();

        if (nom.isEmpty() || cin.isEmpty() || consultation.isEmpty() || prixText.isEmpty()) {
            showAlert("❌ Tous les champs sont requis.", "danger");
            return;
        }

        try {
            double prix = Double.parseDouble(prixText);
            if (prix < 0) {
                showAlert("❌ Prix invalide.", "danger");
                return;
            }

            if (selectedPatient == null) {
                Patient newPatient = new Patient(nom, cin, consultation, prix);
                patients.add(newPatient);
                showAlert("✅ Patient ajouté !", "success");
            } else {
                selectedPatient.setNom(nom);
                selectedPatient.setCin(cin);
                selectedPatient.setConsultation(consultation);
                selectedPatient.setPrix(prix);
                patientTable.refresh();
                showAlert("✅ Patient modifié !", "success");
                selectedPatient = null;
                addOrUpdateButton.setText("➕ Ajouter");
            }

            nomField.clear();
            cinField.clear();
            consultationField.clear();
            prixField.clear();

        } catch (NumberFormatException e) {
            showAlert("❌ Le prix doit être un nombre.", "danger");
        }
    }

    private void showAlert(String message, String type) {
        alertLabel.setText(message);
        alertLabel.setVisible(true);
        alertLabel.getStyleClass().removeAll("alert-success", "alert-danger");

        alertLabel.getStyleClass().add(type.equals("success") ? "alert-success" : "alert-danger");

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                alertLabel.setVisible(false);
            } catch (InterruptedException ignored) {}
        }).start();
    }
}