package com.ds.stock;

import com.ds.stock.data.*;
import com.ds.stock.pages.AddEditDataPage;
import com.ds.stock.pages.Page;
import com.ds.stock.utils.Utils;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.enums.DataType;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ds.stock.utils.Utils.getSelectedRowIndexFromTableView;

public final class TableViewDataController {
    public static void displayAppliedInvoicesForPurchasedGoods(@NotNull TableViewController tableViewController, List<AppliedInvoiceForPurchaseGoodData> appliedInvoiceForPurchaseGoodDataList, Page currentPage){
        try{
            tableViewController.clearTableView();

            TableColumn<AppliedInvoiceForPurchaseGoodData, Long> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

            TableColumn<AppliedInvoiceForPurchaseGoodData, Long> invoiceIdColumn = new TableColumn<>("ID поставщика");
            invoiceIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getProviderId()).asObject());

            TableColumn<AppliedInvoiceForPurchaseGoodData, String> dateColumn = new TableColumn<>("Дата");
            dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

            TableColumn<AppliedInvoiceForPurchaseGoodData, String> goodsIdsColumn = new TableColumn<>("ID товаров");
            goodsIdsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Utils.convertGoodsListToString(cellData.getValue().getGoods())));

            TableColumn<AppliedInvoiceForPurchaseGoodData, Integer> goodsCountColumn = new TableColumn<>("Количество товаров");
            goodsCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGoodsCount()).asObject());

            tableViewController.getTableView().getColumns().addAll(idColumn, invoiceIdColumn, dateColumn, goodsIdsColumn, goodsCountColumn);
            tableViewController.getTableView().getItems().addAll(appliedInvoiceForPurchaseGoodDataList);

            tableViewController.getTableView().setOnMouseClicked(mouseEvent -> {
                int selectedIndex = getSelectedRowIndexFromTableView(tableViewController.getTableView());
                if(selectedIndex < 0)
                    return;

                openEditPage(currentPage, DataType.APPLIED_INVOICES_FOR_PURCHASED_GOODS, (Data) tableViewController.getTableView().getItems().get(selectedIndex));
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void displayCustomers(@NotNull TableViewController tableViewController, List<CustomerData> customerDataList, Page currentPage){
        try{
            tableViewController.clearTableView();

            TableColumn<CustomerData, Long> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

            TableColumn<CustomerData, String> nameColumn = new TableColumn<>("Имя");
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<CustomerData, String> addressColumn = new TableColumn<>("Адрес");
            addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

            TableColumn<CustomerData, String> telephoneColumn = new TableColumn<>("Телефон");
            telephoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));

            TableColumn<CustomerData, Long> invoiceIdColumn = new TableColumn<>("ID счета-фактуры");
            invoiceIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getInvoiceDataId()).asObject());

            tableViewController.getTableView().getColumns().addAll(idColumn, nameColumn, addressColumn, telephoneColumn, invoiceIdColumn);
            tableViewController.getTableView().getItems().addAll(customerDataList);

            tableViewController.getTableView().setOnMouseClicked(mouseEvent -> {
                int selectedIndex = getSelectedRowIndexFromTableView(tableViewController.getTableView());
                if(selectedIndex < 0)
                    return;

                openEditPage(currentPage, DataType.CUSTOMERS, (Data) tableViewController.getTableView().getItems().get(selectedIndex));
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }

    }

    public static void displayProviders(@NotNull TableViewController tableViewController, List<ProviderData> providerDataList, Page currentPage){
        try{
            tableViewController.clearTableView();

            TableColumn<ProviderData, Long> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

            TableColumn<ProviderData, Long> invoiceIdColumn = new TableColumn<>("ID приходной накладной");
            invoiceIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getAppliedInvoiceForPurchaseGood()).asObject());

            TableColumn<ProviderData, String> nameColumn = new TableColumn<>("Имя");
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<ProviderData, String> addressColumn = new TableColumn<>("Адрес");
            addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

            TableColumn<ProviderData, String> telephoneColumn = new TableColumn<>("Телефон");
            telephoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));

            tableViewController.getTableView().getColumns().addAll(idColumn, nameColumn, addressColumn, telephoneColumn, invoiceIdColumn);
            tableViewController.getTableView().getItems().addAll(providerDataList);

            tableViewController.getTableView().setOnMouseClicked(mouseEvent -> {
                int selectedIndex = getSelectedRowIndexFromTableView(tableViewController.getTableView());
                if(selectedIndex < 0)
                    return;

                openEditPage(currentPage, DataType.PROVIDERS, (Data) tableViewController.getTableView().getItems().get(selectedIndex));
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void displayInvoices(@NotNull TableViewController tableViewController, List<InvoiceData> invoiceDataList, Page currentPage){
        try{
            tableViewController.clearTableView();

            TableColumn<InvoiceData, Long> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

            TableColumn<InvoiceData, Long> customerIdColumn = new TableColumn<>("ID покупателя");
            customerIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCustomerDataId()).asObject());

            TableColumn<InvoiceData, String> dateColumn = new TableColumn<>("Дата");
            dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

            TableColumn<InvoiceData, String> goodsIdColumn = new TableColumn<>("ID товаров");
            goodsIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGoods()));

            TableColumn<InvoiceData, Integer> goodsCountColumn = new TableColumn<>("Количество товаров");
            goodsCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGoodsCount()).asObject());

            tableViewController.getTableView().getColumns().addAll(idColumn, customerIdColumn, dateColumn, goodsIdColumn, goodsCountColumn);
            tableViewController.getTableView().getItems().addAll(invoiceDataList);

            tableViewController.getTableView().setOnMouseClicked(mouseEvent -> {
                int selectedIndex = getSelectedRowIndexFromTableView(tableViewController.getTableView());
                if(selectedIndex < 0)
                    return;

                openEditPage(currentPage, DataType.INVOICES, (Data) tableViewController.getTableView().getItems().get(selectedIndex));
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void displayLocations(@NotNull TableViewController tableViewController, List<LocationData> locationDataList, Page currentPage){
        try {
            tableViewController.clearTableView();

            TableColumn<LocationData, Long> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

            TableColumn<LocationData, String> nameColumn = new TableColumn<>("Название");
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<LocationData, String> addressColumn = new TableColumn<>("Адрес");
            addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

            TableColumn<LocationData, String> telephoneColumn = new TableColumn<>("Телефон");
            telephoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));

            tableViewController.getTableView().getColumns().addAll(idColumn, nameColumn, addressColumn, telephoneColumn);
            tableViewController.getTableView().getItems().addAll(locationDataList);

            tableViewController.getTableView().setOnMouseClicked(mouseEvent -> {
                int selectedIndex = getSelectedRowIndexFromTableView(tableViewController.getTableView());
                if(selectedIndex < 0)
                    return;

                openEditPage(currentPage, DataType.LOCATIONS, (Data) tableViewController.getTableView().getItems().get(selectedIndex));
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void displayGoods(@NotNull TableViewController tableViewController, List<GoodData> goodDataList, Page currentPage){
        try {
            tableViewController.clearTableView();

            TableColumn<GoodData, Long> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

            TableColumn<GoodData, String> nameColumn = new TableColumn<>("Название");
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<GoodData, String> unitColumn = new TableColumn<>("Единица измерения");
            unitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnit()));

            TableColumn<GoodData, Double> purchaseCostColumn = new TableColumn<>("Цена покупки");
            purchaseCostColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPurchaseCost()).asObject());

            TableColumn<GoodData, Double> saleCostColumn = new TableColumn<>("Цена продажи");
            saleCostColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSaleCost()).asObject());

            tableViewController.getTableView().getColumns().addAll(idColumn, nameColumn, unitColumn, purchaseCostColumn, saleCostColumn);
            tableViewController.getTableView().getItems().addAll(goodDataList);

            tableViewController.getTableView().setOnMouseClicked(mouseEvent -> {
                int selectedIndex = getSelectedRowIndexFromTableView(tableViewController.getTableView());
                if(selectedIndex < 0)
                    return;

                openEditPage(currentPage, DataType.GOODS, (Data) tableViewController.getTableView().getItems().get(selectedIndex));
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private static void openEditPage(Page currentPage, @NotNull DataType dataType, Data data) {
        AddEditDataPage editDataPage = new AddEditDataPage(currentPage, currentPage.getContentVbox(), "Редактирование", true);
        editDataPage.open();

        switch (dataType){
            case GOODS -> editDataPage.createGoodsComponents((GoodData) data);
            case INVOICES -> editDataPage.createInvoicesComponents((InvoiceData) data);
            case CUSTOMERS -> editDataPage.createCustomerComponents((CustomerData) data);
            case LOCATIONS -> editDataPage.createLocationsComponents((LocationData) data);
            case PROVIDERS -> editDataPage.createProviderComponents((ProviderData) data);
            case APPLIED_INVOICES_FOR_PURCHASED_GOODS -> editDataPage.createAppliedInvoicesForPurchasedGoodsComponents((AppliedInvoiceForPurchaseGoodData) data);
        }
    }
}
