package com.ds.stock.data.dataUtils.writer;

import com.ds.stock.Constants;
import com.ds.stock.data.*;
import com.ds.stock.database.DatabaseService;
import com.ds.stock.database.tables.*;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.settings.SettingsManager;

import java.sql.PreparedStatement;
import java.util.Objects;

import static com.ds.stock.utils.Utils.convertGoodsListToString;

public final class DataWriter {
    public static void addGood(GoodData goodData){
        try {
            String add = "INSERT INTO " + Goods.TABLE_NAME + "(" + Goods.NAME_ROW + "," + Goods.UNIT_ROW + "," + Goods.PURCHASE_COST_ROW + "," + Goods.SALE_COST_ROW + ") VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(add);
            preparedStatement.setString(1, goodData.getName());
            preparedStatement.setString(2, goodData.getUnit());
            preparedStatement.setDouble(3, goodData.getPurchaseCost());
            preparedStatement.setDouble(4, goodData.getSaleCost());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addLocation(LocationData locationData){
        try {
            String add = "INSERT INTO " + Locations.TABLE_NAME + "(" + Locations.NAME_ROW + "," + Locations.ADDRESS_ROW + "," + Locations.TELEPHONE_ROW + ") VALUES(?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(add);
            preparedStatement.setString(1, locationData.getName());
            preparedStatement.setString(2, locationData.getAddress());
            preparedStatement.setString(3, locationData.getTelephone());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addProvider(ProviderData providerData){
        try {
            String add = "INSERT INTO " + Providers.TABLE_NAME + "(" + Providers.NAME_ROW + "," + Providers.ADDRESS_ROW + "," + Providers.TELEPHONE_ROW + "," + Providers.APPLIED_INVOICE_FOR_PURCHASED_GOOD_ID_ROW + ") VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(add);
            preparedStatement.setString(1, providerData.getName());
            preparedStatement.setString(2, providerData.getAddress());
            preparedStatement.setString(3, providerData.getTelephone());
            preparedStatement.setObject(4, providerData.getAppliedInvoiceForPurchaseGood());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addCustomer(CustomerData customerData){
        try {
            String add = "INSERT INTO " + Customers.TABLE_NAME + "(" + Customers.NAME_ROW + "," + Customers.ADDRESS_ROW + "," + Customers.TELEPHONE_ROW + "," + Customers.INVOICE_ID_ROW + ") VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(add);
            preparedStatement.setString(1, customerData.getName());
            preparedStatement.setString(2, customerData.getAddress());
            preparedStatement.setString(3, customerData.getTelephone());
            preparedStatement.setObject(4, customerData.getInvoiceData());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addInvoice(InvoiceData invoiceData){
        try {
            String add = "INSERT INTO " + Invoices.TABLE_NAME + "(" + Invoices.CUSTOMER_ID_ROW + "," + Invoices.DATE_ROW + "," + Invoices.GOODS_IDS_ROW + "," + Invoices.GOODS_COUNT_ROW + ") VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(add);
            preparedStatement.setLong(1, invoiceData.getCustomerDataId());
            preparedStatement.setString(2, invoiceData.getDate());
            preparedStatement.setObject(3, invoiceData.getGoods());
            preparedStatement.setInt(4, invoiceData.getGoodsCount());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addAppliedInvoicesForPurchaseGoods(AppliedInvoiceForPurchaseGoodData appliedInvoicesForPurchaseGoods){
        try {
            String add = "INSERT INTO " + AppliedInvoicesForPurchaseGoods.TABLE_NAME + "(" + AppliedInvoicesForPurchaseGoods.DATE_ROW + "," + AppliedInvoicesForPurchaseGoods.GOODS_IDS_ROW + "," + AppliedInvoicesForPurchaseGoods.PROVIDER_ID_ROW + "," + AppliedInvoicesForPurchaseGoods.GOODS_COUNT_ROW + ") VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(add);
            preparedStatement.setString(1, appliedInvoicesForPurchaseGoods.getDate());
            preparedStatement.setString(2, convertGoodsListToString(appliedInvoicesForPurchaseGoods.getGoods()));
            preparedStatement.setLong(3, appliedInvoicesForPurchaseGoods.getProviderId());
            preparedStatement.setInt(4, appliedInvoicesForPurchaseGoods.getGoodsCount());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
