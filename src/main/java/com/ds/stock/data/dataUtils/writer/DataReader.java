package com.ds.stock.data.dataUtils.writer;

import com.ds.stock.data.*;
import com.ds.stock.database.DatabaseService;
import com.ds.stock.database.tables.*;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.settings.SettingsManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ds.stock.Constants.CURRENT_DATABASE_FILE_KEY;
import static com.ds.stock.database.DefaultTablesConstants.ID_ROW;
import static com.ds.stock.utils.Utils.convertStringToGoodDataList;

public final class DataReader {
    @Contract(pure = true)
    private static @NotNull String getSelectRequest(String tableName){
        return "SELECT * FROM " + tableName + " ORDER BY " + ID_ROW + " ASC";
    }

    public static @Nullable List<GoodData> getAllGoods(){
        try{
            String selectAll = getSelectRequest(Goods.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<GoodData> goodDataArrayList = new ArrayList<>();
            while (resultSet.next()){
                goodDataArrayList.add(new GoodData(resultSet.getLong(ID_ROW), resultSet.getString(Goods.NAME_ROW), resultSet.getDouble(Goods.PURCHASE_COST_ROW),
                        resultSet.getDouble(Goods.SALE_COST_ROW), resultSet.getString(Goods.UNIT_ROW)));
            }

            return goodDataArrayList;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<LocationData> getAllLocations(){
        try{
            String selectAll = getSelectRequest(Locations.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<LocationData> locationDataArrayList = new ArrayList<>();
            while (resultSet.next()){
                locationDataArrayList.add(new LocationData(resultSet.getLong(ID_ROW), resultSet.getString(Locations.NAME_ROW),
                        resultSet.getString(Locations.ADDRESS_ROW), resultSet.getString(Locations.TELEPHONE_ROW)));
            }

            return locationDataArrayList;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<ProviderData> getAllProviders(){
        try{
            String selectAll = getSelectRequest(Providers.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ProviderData> providerDataArrayList = new ArrayList<>();
            while (resultSet.next()){
                providerDataArrayList.add(new ProviderData(resultSet.getLong(ID_ROW), resultSet.getString(Providers.NAME_ROW),
                        resultSet.getString(Providers.ADDRESS_ROW), resultSet.getString(Providers.TELEPHONE_ROW), resultSet.getLong(Providers.APPLIED_INVOICE_FOR_PURCHASED_GOOD_ID_ROW)));
            }

            return providerDataArrayList;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<CustomerData> getAllCustomers(){
        try{
            String selectAll = getSelectRequest(Customers.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CustomerData> customerDataArrayList = new ArrayList<>();
            while (resultSet.next()){
                customerDataArrayList.add(new CustomerData(resultSet.getLong(ID_ROW), resultSet.getString(Customers.NAME_ROW),
                        resultSet.getString(Customers.ADDRESS_ROW), resultSet.getString(Customers.TELEPHONE_ROW), resultSet.getLong(Customers.INVOICE_ID_ROW)));
            }

            return customerDataArrayList;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<InvoiceData> getAllInvoice(){
        try{
            String selectAll = getSelectRequest(Invoices.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<InvoiceData> customerDataArrayList = new ArrayList<>();
            while (resultSet.next()){
                customerDataArrayList.add(new InvoiceData(resultSet.getLong(ID_ROW), resultSet.getLong(Invoices.CUSTOMER_ID_ROW), resultSet.getString(Invoices.DATE_ROW),
                        resultSet.getString(Invoices.GOODS_IDS_ROW)));
            }

            return customerDataArrayList;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<AppliedInvoiceForPurchaseGoodData> getAllAppliedInvoiceForPurchaseGoodData(){
        try{
            String selectAll = getSelectRequest(AppliedInvoicesForPurchaseGoods.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AppliedInvoiceForPurchaseGoodData> appliedInvoiceForPurchaseGoodDataArrayList = new ArrayList<>();
            while (resultSet.next()){
                appliedInvoiceForPurchaseGoodDataArrayList.add(new AppliedInvoiceForPurchaseGoodData(resultSet.getLong(ID_ROW), resultSet.getLong(AppliedInvoicesForPurchaseGoods.PROVIDER_ID_ROW),
                        resultSet.getString(AppliedInvoicesForPurchaseGoods.DATE_ROW), convertStringToGoodDataList(resultSet.getString(AppliedInvoicesForPurchaseGoods.GOODS_IDS_ROW))));
            }

            return appliedInvoiceForPurchaseGoodDataArrayList;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }
}
