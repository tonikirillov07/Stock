package com.ds.stock;

import com.ds.stock.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.ds.stock.Constants.*;
import static com.ds.stock.utils.Utils.getImage;

public class Main extends Application {
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.getIcons().add(getImage("images/icon/icon.png"));
        stage.setTitle(TITLE);
        stage.setScene(scene);

        MainController mainController = fxmlLoader.getController();
        mainController.init(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}