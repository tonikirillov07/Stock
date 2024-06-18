package com.ds.stock.pages;

import com.ds.stock.TableViewController;
import com.ds.stock.TableViewDataController;
import com.ds.stock.additionalNodes.AdditionalScrollPane;
import com.ds.stock.additionalNodes.AdditionalTextField;
import com.ds.stock.additionalNodes.CategoryMenuButton;
import com.ds.stock.data.*;
import com.ds.stock.data.dataUtils.writer.DataReader;
import com.ds.stock.utils.Utils;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.enums.DataType;
import com.ds.stock.utils.enums.InputTypes;
import com.ds.stock.utils.eventListeners.IOnTextTyping;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.util.List;

public class DataFinderPage extends Page{
    private VBox scrollPaneVbox;
    private DataType searchMode;
    private AdditionalTextField dataIdField;
    private TableViewController tableViewController;

    protected DataFinderPage(Page prevoiusPage, VBox contentVbox, String title) {
        super(prevoiusPage, contentVbox, title);
    }

    @Override
    public void onOpen() {
        loadDefaultTileSettings();

        createScrollPane();
        createDataTypeSelector();
        createTextField();
        createTableView();
    }

    private void createTableView() {
        tableViewController = new TableViewController();
        tableViewController.createTableView(scrollPaneVbox);
    }

    private void createDataTypeSelector() {
        try {
            CategoryMenuButton categoryMenuButton = new CategoryMenuButton();
            scrollPaneVbox.getChildren().add(categoryMenuButton);

            categoryMenuButton.getGoodsMenuButton().setOnAction(actionEvent -> {
                Utils.defaultCategoryMenuItemsAction(categoryMenuButton.getGoodsMenuButton(), categoryMenuButton);
                searchMode = DataType.GOODS;

                dataIdField.getTextField().setPromptText("ID товара");
            });
            categoryMenuButton.getLocationsMenuButton().setOnAction(actionEvent -> {
                Utils.defaultCategoryMenuItemsAction(categoryMenuButton.getLocationsMenuButton(), categoryMenuButton);
                searchMode = DataType.LOCATIONS;

                dataIdField.getTextField().setPromptText("ID локации");
            });
            categoryMenuButton.getCustomersMenuButton().setOnAction(actionEvent -> {
                Utils.defaultCategoryMenuItemsAction(categoryMenuButton.getCustomersMenuButton(), categoryMenuButton);
                searchMode = DataType.CUSTOMERS;

                dataIdField.getTextField().setPromptText("ID покупателя");
            });
            categoryMenuButton.getProvidersMenuButton().setOnAction(actionEvent -> {
                Utils.defaultCategoryMenuItemsAction(categoryMenuButton.getProvidersMenuButton(), categoryMenuButton);
                searchMode = DataType.PROVIDERS;

                dataIdField.getTextField().setPromptText("ID поставщика");
            });
            categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton().setOnAction(actionEvent -> {
                Utils.defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForGoodsSoldMenuButton(), categoryMenuButton);
                searchMode = DataType.INVOICES;

                dataIdField.getTextField().setPromptText("ID накладной к проданным товарам");
            });
            categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton().setOnAction(actionEvent -> {
                Utils.defaultCategoryMenuItemsAction(categoryMenuButton.getAppliedInvoicesForPurchasedGoodsMenuButton(), categoryMenuButton);
                searchMode = DataType.APPLIED_INVOICES_FOR_PURCHASED_GOODS;

                dataIdField.getTextField().setPromptText("ID накладной к купленным товарам");
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void createTextField() {
        try {
            dataIdField = new AdditionalTextField(AdditionalTextField.DEFAULT_WIDTH, AdditionalTextField.DEFAULT_HEIGHT, "", Utils.getImage("images/search.png"), false);
            scrollPaneVbox.getChildren().add(dataIdField);

            dataIdField.setInputType(InputTypes.NUMERIC);
            dataIdField.addOnTextTyping(currentText -> {
                if (currentText.isEmpty() | searchMode == null)
                    return;

                long enteredId = Long.parseLong(currentText);

                switch (searchMode) {
                    case GOODS -> findAndDisplayGood(enteredId);
                    case LOCATIONS -> findAndDisplayLocation(enteredId);
                    case CUSTOMERS -> findAndDisplayCustomer(enteredId);
                    case PROVIDERS -> findAndDisplayProvider(enteredId);
                    case INVOICES -> findAndDisplayInvoice(enteredId);
                    case APPLIED_INVOICES_FOR_PURCHASED_GOODS ->
                            findAndDisplayAppliedInvoicesForPurchasedGoods(enteredId);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void findAndDisplayAppliedInvoicesForPurchasedGoods(long id){
        try {
            AppliedInvoiceForPurchaseGoodData appliedInvoiceForPurchaseGoodData = AppliedInvoiceForPurchaseGoodData.findAppliedInvoiceForPurchaseGoodDataById(id);
            if (appliedInvoiceForPurchaseGoodData == null) {
                tableViewController.clearTableView();
                return;
            }

            TableViewDataController.displayAppliedInvoicesForPurchasedGoods(tableViewController, List.of(appliedInvoiceForPurchaseGoodData), this);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void findAndDisplayInvoice(long id){
        try {
            InvoiceData invoiceData = InvoiceData.findInvoiceDataById(id);
            if (invoiceData == null) {
                tableViewController.clearTableView();
                return;
            }

            TableViewDataController.displayInvoices(tableViewController, List.of(invoiceData), this);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void findAndDisplayProvider(long id){
        try {
            ProviderData providerData = ProviderData.findProviderById(id);
            if (providerData == null) {
                tableViewController.clearTableView();
                return;
            }

            TableViewDataController.displayProviders(tableViewController, List.of(providerData), this);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void findAndDisplayCustomer(long id){
        try {
            CustomerData customerData = CustomerData.findCustomerById(id);
            if (customerData == null) {
                tableViewController.clearTableView();
                return;
            }

            TableViewDataController.displayCustomers(tableViewController, List.of(customerData), this);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void findAndDisplayLocation(long id){
        try {
            List<LocationData> locationDataList = DataReader.getAllLocations();
            LocationData locationData = null;

            assert locationDataList != null;
            for (LocationData data : locationDataList) {
                if (data.getId() == id) {
                    locationData = data;
                    break;
                }
            }

            if (locationData == null) {
                tableViewController.clearTableView();
                return;
            }

            TableViewDataController.displayLocations(tableViewController, List.of(locationData), this);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void findAndDisplayGood(long id){
        try {
            GoodData goodData = GoodData.findGoodById(id);
            if (goodData == null) {
                tableViewController.clearTableView();
                return;
            }

            TableViewDataController.displayGoods(tableViewController, List.of(goodData), this);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void createScrollPane() {
        try {
            AdditionalScrollPane additionalScrollPane = new AdditionalScrollPane();
            additionalScrollPane.createScrollPane();

            scrollPaneVbox = additionalScrollPane.getScrollPaneContentVbox();
            scrollPaneVbox.setPadding(new Insets(0d, 50d, 0d, 50d));

            addNodeToTile(additionalScrollPane.getScrollPane());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
