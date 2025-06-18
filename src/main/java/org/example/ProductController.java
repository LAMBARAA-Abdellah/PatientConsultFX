package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;

public class ProductController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ListView<Product> productListView;

    private ObservableList<Product> products;

    @FXML
    public void initialize() {
        products = FXCollections.observableArrayList();
        productListView.setItems(products);
    }

    @FXML
    public void handleAddProduct() {
        String name = nameField.getText();
        String priceText = priceField.getText();

        if (name.isEmpty() || priceText.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            Product product = new Product(name, price);
            products.add(product);
            nameField.clear();
            priceField.clear();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le prix doit être un nombre valide.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
