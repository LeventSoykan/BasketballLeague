package com.fbasketball;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader =  new FXMLLoader(Main.class.getResource("main_screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 600, Color.AQUA);
        primaryStage.setTitle("Basketball League Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        launch(args);





    }
}
