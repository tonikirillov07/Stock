package com.ds.stock.additionalNodes;

import javafx.scene.control.MenuItem;

public class CategoryMenuButton extends AdditionalMenuButton{
    private final MenuItem locationsMenuButton, goodsMenuButton, providersMenuButton, appliedInvoicesForPurchasedGoodsMenuButton, customersMenuButton, appliedInvoicesForGoodsSoldMenuButton;

    public CategoryMenuButton() {
        locationsMenuButton = new MenuItem("Локации");
        goodsMenuButton = new MenuItem("Товары");
        providersMenuButton = new MenuItem("Поставщики");
        appliedInvoicesForPurchasedGoodsMenuButton = new MenuItem("Приходные накладные к купленным товарам");
        customersMenuButton = new MenuItem("Покупатели");
        appliedInvoicesForGoodsSoldMenuButton = new MenuItem("Приходные накладные к проданным товарам");

        setText("Категория данных");
        getItems().addAll(locationsMenuButton, goodsMenuButton, providersMenuButton, appliedInvoicesForPurchasedGoodsMenuButton, customersMenuButton, appliedInvoicesForGoodsSoldMenuButton);
    }

    public MenuItem getLocationsMenuButton() {
        return locationsMenuButton;
    }

    public MenuItem getGoodsMenuButton() {
        return goodsMenuButton;
    }

    public MenuItem getProvidersMenuButton() {
        return providersMenuButton;
    }

    public MenuItem getAppliedInvoicesForPurchasedGoodsMenuButton() {
        return appliedInvoicesForPurchasedGoodsMenuButton;
    }

    public MenuItem getCustomersMenuButton() {
        return customersMenuButton;
    }

    public MenuItem getAppliedInvoicesForGoodsSoldMenuButton() {
        return appliedInvoicesForGoodsSoldMenuButton;
    }
}
