package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

//import static com.sun.scenario.effect.impl.prism.PrEffectHelper.render;
//import static jdk.jfr.internal.consumer.EventLog.update;

public class ArkanoidController {
    @FXML
    private Canvas gameCanvas = new Canvas(600, 800);

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
                gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
                Renderer.getInstance().renderBackground(GameEngine.getLevel());
                Renderer.getInstance().render(GameEngine.getPaddle());
                Renderer.getInstance().render(GameEngine.getBall());

                if (!gameEngine.gameOver()) {
                    try {
                        gameEngine.updateGame();
                    } catch (IOException | URISyntaxException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    gameEngine.handleInput(gameCanvas);
                    gameEngine.checkCollision();

                    gc.setFill(Color.BLACK);
                    gc.setFont(Font.font("Arial", GameConst.FontSize));
                    gc.fillText(String.format("Level %d", GameEngine.getLevel().getLvl()), 0,
                            GameConst.HEIGHT - GameConst.FontSize);
                    gc.fillText(String.format("Score: %d", gameEngine.getScore()), 350,
                            GameConst.HEIGHT - GameConst.FontSize);
                    gc.fillText(String.format("Live(s): %d", gameEngine.getLives()), 700,
                            GameConst.HEIGHT - GameConst.FontSize);
                } else {
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

}
