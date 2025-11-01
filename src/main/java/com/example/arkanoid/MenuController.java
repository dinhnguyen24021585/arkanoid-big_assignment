package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    public void startGame() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));
        gameScene = new Scene(loader.load());

        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setScene(gameScene);

        GameEngine.getLevel().setLvl(1);
    }

    @FXML
    public void loadGame() throws Exception {

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
