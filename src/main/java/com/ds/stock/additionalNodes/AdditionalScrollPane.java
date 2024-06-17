package com.ds.stock.additionalNodes;

import com.ds.stock.utils.dialogs.ErrorDialog;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AdditionalScrollPane {
    private ScrollPane scrollPane;
    private VBox scrollPaneContentVbox;

    public void createScrollPane(){
        try {
            scrollPane = new ScrollPane();
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setFitToWidth(true);
            VBox.setVgrow(scrollPane, Priority.ALWAYS);

            scrollPaneContentVbox = new VBox();
            scrollPaneContentVbox.setSpacing(15d);
            scrollPaneContentVbox.setAlignment(Pos.TOP_CENTER);
            scrollPane.setContent(scrollPaneContentVbox);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public VBox getScrollPaneContentVbox() {
        return scrollPaneContentVbox;
    }
}
