package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ArkanoidController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Xin chào từ JavaFX!");
    }
}
