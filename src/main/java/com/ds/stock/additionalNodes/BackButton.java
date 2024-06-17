package com.ds.stock.additionalNodes;

import com.ds.stock.pages.Page;
import com.ds.stock.utils.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class BackButton {
    public static void createBackButton(Page page, @NotNull Pane parent){
        try {
            AdditionalButton backButton = new AdditionalButton("Назад", 200d, 40d, "#15604E", Color.WHITE);
            VBox.setMargin(backButton, new Insets(0d, 0d, 15d, 0d));
            backButton.setOnAction(actionEvent -> page.goToPreviousPage());

            parent.getChildren().add(backButton);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
