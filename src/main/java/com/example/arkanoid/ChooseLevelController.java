package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class ChooseLevelController {
    @FXML
    private AnchorPane pane;
    @FXML
    private Button lvl1;
    @FXML
    private Button lvl2;
    @FXML
    private Button lvl3;

    public void initialize() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                lvl1.setOnAction(e -> GameEngine.getLevel().setLvl(1));
                lvl2.setOnAction(e -> GameEngine.getLevel().setLvl(2));
                lvl3.setOnAction(e -> GameEngine.getLevel().setLvl(3));
            }
        };

        timer.start();


    }

    @FXML
    public void loadLevel1() throws IOException, URISyntaxException {
        System.out.println(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));
        //Scene gameScene = new Scene(loader.load());


        GameEngine.startGame();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(MenuController.gameScene);
    }
    @FXML
    public void loadLevel2() throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));
        Scene gameScene = new Scene(loader.load());


        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(gameScene);System.out.println(0);
        
        GameEngine.startGame();
    }
@FXML
    public void loadLevel3() throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/arkanoid-view.fxml"));
        //Scene gameScene = new Scene(loader.load());

        System.out.println(0);
        GameEngine.startGame();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(MenuController.gameScene);
    }
}

