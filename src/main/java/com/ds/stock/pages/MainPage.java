package com.ds.stock.pages;

import com.ds.stock.Calculations;
import com.ds.stock.TableViewController;
import com.ds.stock.TableViewDataController;
import com.ds.stock.additionalNodes.AdditionalScrollPane;
import com.ds.stock.additionalNodes.CategoryMenuButton;
import com.ds.stock.data.AppliedInvoiceForPurchaseGoodData;
import com.ds.stock.data.GoodData;
import com.ds.stock.data.InvoiceData;
import com.ds.stock.data.dataUtils.writer.DataReader;
import com.ds.stock.utils.Utils;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.dialogs.InfoDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ds.stock.utils.Utils.defaultCategoryMenuItemsAction;

public class MainPage extends Page{
    private VBox scrollPaneContentVbox;
    private final TableViewController tableViewController;

    public MainPage(Page prevoiusPage, VBox contentVbox, String title, MenuBar menuBar) {
        super(prevoiusPage, contentVbox, title);

        tableViewController = new TableViewController();
        createAddMenuItem(menuBar);
        createCalculationsMenuItem(menuBar);
    }

    private void createCalculationsMenuItem(@NotNull MenuBar menuBar) {
        if(menuBar.getMenus().size() > 2)
            return;

        MenuItem providersCostMenuItem = new MenuItem("Себестоимость продукции, закупленной у поставщиков");
        MenuItem customersCostMenuItem = new MenuItem("Себестоимость продукции, реализованной покупателям");
        MenuItem reserveMenuItem = new MenuItem("Запасы продукции");

        reserveMenuItem.setOnAction(actionEvent -> {
            int sum = 0;
            for (AppliedInvoiceForPurchaseGoodData allAppliedInvoiceForPurchaseGoodDatum : Objects.requireNonNull(DataReader.getAllAppliedInvoiceForPurchaseGoodData())) {
                sum += Calculations.getReserve(allAppliedInvoiceForPurchaseGoodDatum);
            }

            InfoDialog.show("Запасы продукции: " + sum);
        });

        customersCostMenuItem.setOnAction(actionEvent -> {
            List<GoodData> goodDataList = new ArrayList<>();

            for (InvoiceData invoiceData : Objects.requireNonNull(DataReader.getAllInvoice())) {
                goodDataList.addAll(Arrays.asList(Utils.convertStringToGoodDataList(invoiceData.getGoods())));
            }

            InfoDialog.show("Себестоимость продукции, реализованной покупателям: " + Calculations.calculateCost(Utils.convertArrayListToDefaultArray(goodDataList)));
        });

        providersCostMenuItem.setOnAction(actionEvent -> {
            List<GoodData> goodDataList = new ArrayList<>();

            for (AppliedInvoiceForPurchaseGoodData allAppliedInvoiceForPurchaseGoodDatum : Objects.requireNonNull(DataReader.getAllAppliedInvoiceForPurchaseGoodData())) {
                goodDataList.addAll(Arrays.asList(allAppliedInvoiceForPurchaseGoodDatum.getGoods()));
            }

            InfoDialog.show("Себестоимость продукции, закупленной у поставщиков: " + Calculations.calculateCost(Utils.convertArrayListToDefaultArray(goodDataList)));
        });

        Menu menuCalculations = new Menu("Расчеты");
        menuCalculations.getItems().addAll(providersCostMenuItem, customersCostMenuItem, reserveMenuItem);

        menuBar.getMenus().add(menuCalculations);
    }

    private void createAddMenuItem(@NotNull MenuBar menuBar) {
        if(menuBar.getMenus().size() > 1)
            return;

        Menu menuData = new Menu("Данные");
        MenuItem menuItemAdd = new MenuItem("Добавить");
        MenuItem menuItemFindGood = new MenuItem("Поиск данных");

        menuItemFindGood.setOnAction(actionEvent -> {
            DataFinderPage dataFinderPage = new DataFinderPage(this, getContentVbox(), "Поиск данных");
            dataFinderPage.open();
        });

        menuItemAdd.setOnAction(actionEvent -> {
            AddEditDataPage addEditDataPage = new AddEditDataPage(this, getContentVbox(), "Добавление", false);
            addEditDataPage.open();
        });

        menuData.getItems().addAll(menuItemAdd, menuItemFindGood);
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
                TableViewDataController.displayProviders(tableViewController, DataReader.getAllProviders(), this);
            });
            categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton(), categoryMenuButton);
                TableViewDataController.displayAppliedInvoicesForPurchasedGoods(tableViewController, DataReader.getAllAppliedInvoiceForPurchaseGoodData(), this);
            });
            categoryMenuButton.getCustomersMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getCustomersMenuButton(), categoryMenuButton);
                TableViewDataController.displayCustomers(tableViewController, DataReader.getAllCustomers(), this);
            });
            categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton().setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton(), categoryMenuButton);
                TableViewDataController.displayInvoices(tableViewController, DataReader.getAllInvoice(), this);
            });

            hBox.getChildren().addAll(categoryMenuButton);
            scrollPaneContentVbox.getChildren().add(hBox);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
