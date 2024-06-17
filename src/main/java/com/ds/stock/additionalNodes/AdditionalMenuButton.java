package com.ds.stock.additionalNodes;

import com.ds.stock.Constants;
import com.ds.stock.Main;
import javafx.scene.control.MenuButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AdditionalMenuButton extends MenuButton {
    public AdditionalMenuButton() {
        init();
    }

    public AdditionalMenuButton(String text) {
        super(text);
        init();
    }

    private void init(){
        setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.FONT_BOLD_ITALIC_PATH), 16d));
        setTextFill(Color.WHITE);
        getStyleClass().add("menu-button-custom");
    }
}
