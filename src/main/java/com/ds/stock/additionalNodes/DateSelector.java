package com.ds.stock.additionalNodes;

import com.ds.stock.Constants;
import com.ds.stock.Main;
import com.ds.stock.utils.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DateSelector extends HBox {
    private DatePicker datePicker;

    public DateSelector() {
        init();
    }

    private void init() {
        try {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5));
            HBox.setHgrow(hBox, Priority.ALWAYS);

            Label label = new Label("Выберете дату: ");
            label.setTextFill(Color.WHITE);
            label.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.FONT_BOLD_ITALIC_PATH), 14d));

            datePicker = new DatePicker();
            datePicker.setPromptText("Дата");

            hBox.getChildren().add(label);
            getChildren().addAll(hBox, datePicker);


        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}
