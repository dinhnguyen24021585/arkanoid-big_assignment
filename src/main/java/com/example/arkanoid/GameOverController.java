package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameOverController {
    @FXML
    private Label scoreLabel;
    @FXML
    private Label highScoreLabel;
    @FXML
    private Button btnReplay;
    @FXML
    private Button btnQuitToMenu;
    @FXML
    private VBox scoreContainer;

    @FXML
    public void initialize() {
        if (GameEngine.isArcadeMode()) {
            scoreLabel.setText(String.format("SCORE: %d", GameEngine.getScore()));
            highScoreLabel.setText(String.format("HIGH SCORE: %d", GameEngine.getHighScore()));
            scoreLabel.setVisible(true);
            highScoreLabel.setVisible(true);
            scoreContainer.setVisible(true);
        } else {
            scoreLabel.setVisible(false);
            highScoreLabel.setVisible(false);
            scoreLabel.setManaged(false);
            highScoreLabel.setManaged(false);
            scoreContainer.setVisible(false);
            scoreContainer.setManaged(false);
        }
    }

    @FXML
    public void handleReplay() throws IOException, URISyntaxException {
        if (GameEngine.isArcadeMode()) {
            GameEngine.saveNewHighScore(GameEngine.getScore());
            GameEngine.getLevel().setLvl(1);
            GameEngine.setScore(0);
        }

        GameEngine.startGame();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/menu.fxml"));
        Scene menuScene = new Scene(loader.load());

        Stage stage = (Stage) btnQuitToMenu.getScene().getWindow();
        stage.setScene(menuScene);
    }
}