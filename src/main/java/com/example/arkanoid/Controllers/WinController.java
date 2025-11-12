package com.example.arkanoid.Controllers;

import com.example.arkanoid.GameEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class WinController {
    @FXML
    private Button btnReplay;
    @FXML
    private Button btnQuitToMenu;

    @FXML
    public void initialize() {
        GameEngine.setGameState(1);
    }

    @FXML
    public void handleReplay() throws IOException, URISyntaxException {
        GameEngine.startGame();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/arkanoid-view.fxml"));
        Scene gameScene = new Scene(loader.load());

        Stage stage = (Stage) btnReplay.getScene().getWindow();
        stage.setScene(gameScene);

        GameEngine.setGameState(0);
        ArkanoidController.gameLoop.start();
    }

    @FXML
    public void handleQuitToMenu() throws IOException {
        GameEngine.setGameState(1);

        ArkanoidController.gameLoop.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/menu.fxml"));
        Scene menuScene = new Scene(loader.load());

        Stage stage = (Stage) btnQuitToMenu.getScene().getWindow();
        stage.setScene(menuScene);
    }
}