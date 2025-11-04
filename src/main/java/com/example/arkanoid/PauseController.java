package com.example.arkanoid;

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

public class PauseController {
    @FXML
    public Button btnContinue;
    public Button btnSave;
    public Button btnReplay;
    public Button btnQuitToMenu;

    @FXML
    public void gameContinue() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));

        Stage stage = (Stage) btnContinue.getScene().getWindow();
        stage.setScene(MenuController.gameScene);
        GameEngine.setGameState(0);
    }

    public void saveGame() {
    }

    @FXML
    public void replay() throws IOException, URISyntaxException {
        GameEngine.getBricks().clear();
        GameEngine.getPowerUps().clear();

        GameEngine.getLevel().setNumOfBricksToLvlUp(0);
        GameEngine.getLevel().loadLevel(GameEngine.getBricks(), GameEngine.getPowerUps());
        GameEngine.getBricks().forEach(b -> b.render());
        GameEngine.getPowerUps().forEach(p -> p.render());

        setLives(GameConst.DefaultLives);
        GameEngine.setGameState(1);
        GameEngine.setScore(0);

        GameEngine.getPaddle().setX(GameConst.DefaultPaddle_X);
        GameEngine.getPaddle().setY(GameConst.DefaultPaddle_Y);
        GameEngine.getPaddle().setWidth(GameConst.PaddleWidth);
        GameEngine.getPaddle().setPaddleSliding(false);

        GameEngine.getBall().setX(GameConst.DefaultBall_X);
        GameEngine.getBall().setY(GameConst.DefaultBall_Y);
        GameEngine.getBall().setSpeed(GameConst.DefaultSpeed);
        GameEngine.getBall().setBallMoving(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));

        Stage stage = (Stage) btnContinue.getScene().getWindow();
        stage.setScene(MenuController.gameScene);
        GameEngine.setGameState(0);

    }

    @FXML
    public void quitToMenu() throws IOException {
        GameEngine.setGameState(0);
        GameEngine.saveStateToFile();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/menu.fxml"));
        Scene menuScene = new Scene(loader.load());

        Stage stage = (Stage) btnQuitToMenu.getScene().getWindow();
        stage.setScene(menuScene);
    }
}
