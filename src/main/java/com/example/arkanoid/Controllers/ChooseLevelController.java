package com.example.arkanoid.Controllers;

import com.example.arkanoid.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChooseLevelController {
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnQuitToMenu;

    private ArrayList<Button> buttons = new ArrayList<>();

    @FXML
    public void initialize() {

        for (int i = 1; i <= 10; i++) {
            //ImageView imageView = new ImageView(getClass().getResource("/com/example/arkanoid/" + i + ".png").toExternalForm());
            Button btn = new Button(String.valueOf(i));
            buttons.add(btn);
            String path = "/com/example/arkanoid/Image/chooseLvlButtons" + i + ".png";

            btn.setPrefWidth(400);
            btn.setPrefHeight(400);

            btn.setStyle(
                    "-fx-background-image: url('" + getClass().getResource(path).toExternalForm() + "');" +
                            "-fx-background-size: 100% 100%;" +
                            "-fx-background-repeat: no-repeat;" +
                            "-fx-background-position: center;" +
                            "-fx-text-fill: transparent;"
            );
            btn.getStyleClass().add("image-button");

            btn.setOnAction(this::loadLevel);


            int col = (i - 1) % 5;
            int row = (i - 1) / 5;
            gridPane.add(btn, col, row);
        }

    }

    @FXML
    public void loadLevel(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/arkanoid-view.fxml"));
            Button clicked = (Button) event.getSource();
            int level = Integer.parseInt(clicked.getText());
            MenuController.gameScene = new Scene(fxmlLoader.load());
            GameEngine.setArcadeMode(false);
            GameEngine.setScore(0);

            GameEngine.getLevel().setLvl(level);

            GameEngine.startGame();
            GameEngine.setGameState(0);
            Stage stage = (Stage) gridPane.getScene().getWindow();
            stage.setScene(MenuController.gameScene);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void quitToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/fxmls/menu.fxml"));
        Scene menuScene = new Scene(loader.load());

        Stage stage = (Stage) btnQuitToMenu.getScene().getWindow();
        stage.setScene(menuScene);
    }
}

