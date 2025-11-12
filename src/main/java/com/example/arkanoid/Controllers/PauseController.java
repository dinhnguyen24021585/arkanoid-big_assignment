package com.example.arkanoid.Controllers;

import com.example.arkanoid.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import static com.example.arkanoid.GameEngine.setLives;
import static com.example.arkanoid.GameEngine.startGame;

public class PauseController {
    @FXML
    public Button btnContinue;
    public Button btnReplay;
    public Button btnQuitToMenu;

    @FXML
    public void gameContinue() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/arkanoid-view.fxml"));

        Stage stage = (Stage) btnContinue.getScene().getWindow();
        stage.setScene(MenuController.gameScene);
        GameEngine.getInstance().setGameState(0);
    }

    @FXML
    public void replay() throws IOException, URISyntaxException {
        if (GameEngine.getInstance().isArcadeMode()) {
            GameEngine.getInstance().saveNewHighScore(GameEngine.getInstance().getScore());
            GameEngine.getInstance().getLevel().setLvl(1);
            GameEngine.getInstance().setScore(0);
            GameEngine.getInstance().startGame();
        } else {
            GameEngine.getInstance().startGame();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/arkanoid-view.fxml"));

        Stage stage = (Stage) btnContinue.getScene().getWindow();
        stage.setScene(MenuController.gameScene);
        GameEngine.setGameState(0);
    }

    @FXML
    public void quitToMenu() throws IOException {
        GameEngine.setGameState(1);
        GameEngine.saveStateToFile();

        ArkanoidController.gameLoop.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/menu.fxml"));
        Scene menuScene = new Scene(loader.load());

        Stage stage = (Stage) btnQuitToMenu.getScene().getWindow();
        stage.setScene(menuScene);
    }
}
