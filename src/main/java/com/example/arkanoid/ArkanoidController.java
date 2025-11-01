package com.example.arkanoid;

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
    @FXML
    private Button btnReplay;

    private GraphicsContext gc;
    private MouseEvent mouseEvent;

    private GameEngine gameEngine = new GameEngine();

    public void initialize() throws IOException, URISyntaxException {

        gc = gameCanvas.getGraphicsContext2D();
        Renderer.getInstance().setGraphicsContext(gc);

        Level level = new Level(1);

        gameEngine.startGame();
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gameEngine.handleInput(gameCanvas);
                if (getBall().isBallMoving())
                    gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

                if (getGameState() == 0) {
                    try {
                        gameEngine.updateGame();
                    } catch (IOException | URISyntaxException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    gameEngine.checkCollision();

                }
                
                gc.setFill(Color.BLACK);
                gc.setFont(Font.font("Arial", GameConst.FontSize));
                gc.fillText(String.format("Level %d", GameEngine.getLevel().getLvl()), 0,
                        GameConst.HEIGHT - GameConst.FontSize);
                gc.fillText(String.format("Score: %d", gameEngine.getScore()), 350,
                        GameConst.HEIGHT - GameConst.FontSize);
                gc.fillText(String.format("Live(s): %d", GameEngine.getLives()), 700,
                        GameConst.HEIGHT - GameConst.FontSize);

                if (gameEngine.gameOver()){
                    try {
                        gameEngine.startGame();
                    } catch (IOException | URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }

                //if (GameEngine.getLevel().getNumOfBricksToLvlUp() == 0)

            }
        };

        gameLoop.start();

    }

    @FXML
    public void pause() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/pause.fxml"));
        Scene gameScene = new Scene(loader.load());

        Stage stage = (Stage) btnPause.getScene().getWindow();
        stage.setScene(gameScene);

        setGameState(1);
    }



}
