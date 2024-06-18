package com.ds.stock.pages;

import com.ds.stock.TableViewController;
import com.ds.stock.TableViewDataController;
import com.ds.stock.additionalNodes.AdditionalScrollPane;
import com.ds.stock.additionalNodes.CategoryMenuButton;
import com.ds.stock.data.dataUtils.writer.DataReader;
import com.ds.stock.utils.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import static com.ds.stock.utils.Utils.defaultCategoryMenuItemsAction;

public class MainPage extends Page{
    private VBox scrollPaneContentVbox;
    private final TableViewController tableViewController;

    public MainPage(Page prevoiusPage, VBox contentVbox, String title, MenuBar menuBar) {
        super(prevoiusPage, contentVbox, title);

        tableViewController = new TableViewController();
        createAddMenuItem(menuBar);
    }

    private void createAddMenuItem(@NotNull MenuBar menuBar) {
        if(menuBar.getMenus().size() > 1)
            return;

        Menu menuData = new Menu("Данные");
        MenuItem menuItemAdd = new MenuItem("Добавить");
        menuItemAdd.setOnAction(actionEvent -> {
            AddEditDataPage addEditDataPage = new AddEditDataPage(this, getContentVbox(), "Добавление", false);
            addEditDataPage.open();
        });

        menuData.getItems().add(menuItemAdd);
        menuBar.getMenus().add(menuData);
    }

    @Override
    public void onOpen() {
        loadDefaultTileSettings();

        createScrollPane();
        initCategoryMenuButton();

        tableViewController.createTableView(scrollPaneContentVbox);
    }

    private void createScrollPane() {
        AdditionalScrollPane additionalScrollPane = new AdditionalScrollPane();
        additionalScrollPane.createScrollPane();

        scrollPaneContentVbox = additionalScrollPane.getScrollPaneContentVbox();
        addNodeToTile(additionalScrollPane.getScrollPane());
    }

    private void initCategoryMenuButton() {
        try {
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(10d));
            hBox.setAlignment(Pos.CENTER_LEFT);

            CategoryMenuButton categoryMenuButton = new CategoryMenuButton();

            categoryMenuButton.getLocationsMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getLocationsMenuButton(), categoryMenuButton);
                TableViewDataController.displayLocations(tableViewController, DataReader.getAllLocations(), this);
            });
            categoryMenuButton.getGoodsMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getGoodsMenuButton(), categoryMenuButton);
                TableViewDataController.displayGoods(tableViewController, DataReader.getAllGoods(), this);
            });
            categoryMenuButton.getProvidersMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getProvidersMenuButton(), categoryMenuButton);
                TableViewDataController.displayAllProviders(tableViewController, DataReader.getAllProviders(), this);
            });
            categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton(), categoryMenuButton);
                TableViewDataController.displayAllAppliedInvoicesForPurchasedGoods(tableViewController, DataReader.getAllAppliedInvoiceForPurchaseGoodData(), this);
            });
            categoryMenuButton.getCustomersMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getCustomersMenuButton(), categoryMenuButton);
                TableViewDataController.displayAllCustomers(tableViewController, DataReader.getAllCustomers(), this);
            });
            categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton(), categoryMenuButton);
                TableViewDataController.displayAllInvoices(tableViewController, DataReader.getAllInvoice(), this);
            });

            hBox.getChildren().addAll(categoryMenuButton);
            scrollPaneContentVbox.getChildren().add(hBox);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
