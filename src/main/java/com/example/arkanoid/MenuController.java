package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
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
    @FXML
    private Button btnVolume;
    @FXML
    private VBox volumeControlsContainer;
    @FXML
    private Slider bgmSlider;
    @FXML
    private Slider sfxSlider;

    static Scene gameScene;

    @FXML
    public void volumeSliders() {
        boolean isVisible = volumeControlsContainer.isVisible();
        volumeControlsContainer.setVisible(!isVisible);
        volumeControlsContainer.setManaged(!isVisible);
    }

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

    @FXML
    public void initialize() {
        setupVolumeBindings();
    }

    private void setupVolumeBindings() {
        bgmSlider.setValue(Sound.getBgmVolume());
        sfxSlider.setValue(Sound.getSfxVolume());

        bgmSlider.valueProperty().addListener((obs,
                                               oldValue, newValue) -> {
            Sound.setBgmVolume(newValue.doubleValue());
        });

        sfxSlider.valueProperty().addListener((obs,
                                               oldValue, newValue) -> {
            Sound.setSfxVolume(newValue.doubleValue());
        });
    }
}
