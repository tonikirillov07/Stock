package com.ds.stock.controllers;

import com.ds.stock.mainViewInitializer.BackgroundInitializer;
import com.ds.stock.mainViewInitializer.MenuBarInitializer;
import com.ds.stock.pages.MainPage;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private VBox mainVbox;

    @FXML
    private VBox contentVbox;

    @FXML
    private MenuBar menuBar;

    public void init(Stage stage) {
        MenuBarInitializer menuBarInitializer = new MenuBarInitializer();
        BackgroundInitializer backgroundInitializer = new BackgroundInitializer();
        menuBarInitializer.init(menuBar, stage);
        backgroundInitializer.init(mainVbox);

        MainPage mainPage = new MainPage(null, contentVbox, "Данные", menuBar);
        mainPage.open();
    }
}
