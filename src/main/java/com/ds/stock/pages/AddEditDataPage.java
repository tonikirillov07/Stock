package com.ds.stock.pages;

import javafx.scene.layout.VBox;

public class AddDataPage extends Page{
    public AddDataPage(Page prevoiusPage, VBox contentVbox, String title) {
        super(prevoiusPage, contentVbox, title);
    }

    @Override
    public void onOpen() {
        loadDefaultTileSettings();
    }
}
