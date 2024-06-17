package com.ds.stock;

import com.ds.stock.utils.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class TableViewController {
    private TableView tableView;

    public void createTableView(@NotNull Pane parent) {
        try {
            tableView = new TableView();
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            VBox.setVgrow(tableView, Priority.ALWAYS);

            VBox.setMargin(tableView, new Insets(10d));
            parent.getChildren().add(tableView);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void clearTableView(){
        try {
            tableView.getItems().clear();
            tableView.getColumns().clear();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public TableView getTableView() {
        return tableView;
    }
}
