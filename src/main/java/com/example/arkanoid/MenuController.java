package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenuController {
    @FXML
    private Button btnLoad;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnLvls;

    static Scene gameScene;

    @FXML
    public void newGame() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));
        gameScene = new Scene(loader.load());

        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setScene(gameScene);

        GameEngine.getLevel().setLvl(1);
        GameEngine.startGame();
    }

    @FXML
    public void loadGame() throws Exception {
        Stage stage = (Stage) btnLoad.getScene().getWindow();
        stage.setScene(gameScene);
        GameEngine.loadStateFromFile();
        GameEngine.getPaddle().setPaddleSliding(true);
        GameEngine.getBall().setBallMoving(true);
        GameEngine.setGameState(1);
    }

    @FXML
    public void exitGame() {
        System.exit(0);
    }

    @FXML
    public void levelList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/chooseALevel.fxml"));
        gameScene = new Scene(loader.load());

        Stage stage = (Stage) btnLvls.getScene().getWindow();
        stage.setScene(gameScene);
    }
}
