package com.ds.stock.mainViewInitializer;

import com.ds.stock.utils.Utils;
import javafx.scene.layout.Pane;

public class BackgroundInitializer {
    public void init(Pane mainPane){
        Utils.applyBackgroundImageToPane("images/background.png", mainPane, mainPane.getWidth(), mainPane.getHeight());
    }
}
