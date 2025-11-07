package com.example.arkanoid;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;


public class Arkanoid extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/arkanoid/menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setTitle("JavaFX + SceneBuilder Demo");
            Sound.initializeBGM("bgm.mp3");
            Sound.playBGM();

            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
}


