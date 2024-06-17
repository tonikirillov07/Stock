package com.ds.stock.mainViewInitializer;

import com.ds.stock.utils.Utils;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.settings.SettingsManager;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static com.ds.stock.Constants.CURRENT_DATABASE_FILE_KEY;
import static com.ds.stock.utils.Utils.openFileDialog;
import static com.ds.stock.utils.Utils.openSaveDialog;

public class MenuBarInitializer {
    public void init(@NotNull MenuBar menuBar, Stage stage) {
        Menu menuFile = new Menu("Файл");

        initMenuFile(menuFile, stage);

        menuBar.getMenus().addAll(menuFile);
    }

    private void initMenuFile(@NotNull Menu menuFile, Stage stage) {
        try {
            MenuItem menuItemSave = new MenuItem("Сохранить");
            MenuItem menuItemLoad = new MenuItem("Загрузить");

            menuItemLoad.setOnAction(actionEvent -> loadOutsideFile(stage));
            menuItemSave.setOnAction(actionEvent -> saveCurrentFile(stage));

            menuFile.getItems().addAll(menuItemSave, menuItemLoad);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void saveCurrentFile(Stage stage){
        try {
            File selectedFile = openSaveDialog("Выберет файл для сохранения", stage, List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
            if (selectedFile != null) {
                Utils.saveCurrentFile(selectedFile.getAbsolutePath());
            }
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadOutsideFile(Stage stage){
        try {
            File selectedFile = openFileDialog("Выберет файл", stage, List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
            if (selectedFile != null) {
                SettingsManager.changeValue(CURRENT_DATABASE_FILE_KEY, selectedFile.getAbsolutePath());
            }
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

}
