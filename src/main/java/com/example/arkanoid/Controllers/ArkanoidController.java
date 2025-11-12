package com.example.arkanoid.Controllers;

import com.example.arkanoid.GameConst;
import com.example.arkanoid.GameElements.Renderer;
import com.example.arkanoid.GameElements.Sound;
import com.example.arkanoid.GameEngine;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.example.arkanoid.GameEngine.*;

//import static com.sun.scenario.effect.impl.prism.PrEffectHelper.render;
//import static jdk.jfr.internal.consumer.EventLog.update;

public class ArkanoidController {
    @FXML
    private Canvas gameCanvas = new Canvas(600, 800);

    @FXML
    private Button btnPause;

    private GraphicsContext gc;
    private MouseEvent mouseEvent;

    static AnimationTimer gameLoop;

    public void initialize() throws IOException, URISyntaxException {
        gc = gameCanvas.getGraphicsContext2D();
        Renderer.getInstance().setGraphicsContext(gc);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                GameEngine.handleInput(gameCanvas);
                if (getBall().isBallMoving())
                    gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

                if (getGameState() == 0) {
                    try {
                        GameEngine.updateGame();
                        if (GameEngine.isPortalsActive()) {
                            Renderer.getInstance().renderPortals();
                        }

                    } catch (IOException | URISyntaxException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    GameEngine.checkCollision();
                }

                gc.setFill(Color.rgb(255, 230, 245));
                gc.setFont(Font.font("Tahoma", FontWeight.BOLD, GameConst.FontSize));
                gc.fillText(String.format("Level %d", GameEngine.getLevel().getLvl()), 10,
                        GameConst.HEIGHT - GameConst.FontSize);
                gc.fillText(String.format("Live(s): %d", GameEngine.getLives()), 10, GameConst.FontSize);

                if (GameEngine.isArcadeMode()) {
                    gc.fillText(String.format("Score: %d", GameEngine.getScore()), 350,
                            GameConst.HEIGHT - GameConst.FontSize);
                    gc.fillText(String.format("High Score: %d", GameEngine.getHighScore()),
                            600, GameConst.FontSize);
                }

                if (GameEngine.getLevel().getLvl() > GameEngine.MAX_LEVEL) {
                    gameLoop.stop();
                    try {
                        if (GameEngine.getLevel().getLvl() == GameEngine.MAX_LEVEL + 1) {
                            loadWinScreen(); //Arcade Mode
                        } else {
                            loadMainMenu(); //Level Mode
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                if (GameEngine.gameOver()){
                    gameLoop.stop();
                    try {
                        loadGameOverScreen();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        gameLoop.start();
    }

    @FXML
    public void pause() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/pause.fxml"));
        Scene gameScene = new Scene(loader.load());

        Stage stage = (Stage) btnPause.getScene().getWindow();
        stage.setScene(gameScene);

        setGameState(1);
    }

    public void loadGameOverScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/gameOver.fxml"));
        Scene gameOverScene = new Scene(loader.load());

        Stage stage = (Stage) gameCanvas.getScene().getWindow();
        stage.setScene(gameOverScene);
        GameEngine.setGameState(1);
    }

    public void loadMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/menu.fxml"));
        Scene menuScene = new Scene(loader.load());

        Stage stage = (Stage) gameCanvas.getScene().getWindow();
        stage.setScene(menuScene);
        GameEngine.setGameState(1);
    }

    public void loadWinScreen() throws IOException {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        Sound.playSFX("win.mp3");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/win.fxml"));
        Scene winScene = new Scene(loader.load());

        Stage stage = (Stage) gameCanvas.getScene().getWindow();
        stage.setScene(winScene);

        GameEngine.setGameState(1);

        if (GameEngine.isArcadeMode()) {
            GameEngine.saveNewHighScore(GameEngine.getScore());
        }
    }
}
