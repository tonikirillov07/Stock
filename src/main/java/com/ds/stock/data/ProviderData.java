package com.ds.stock.data;

import com.ds.stock.Constants;
import com.ds.stock.database.DatabaseService;
import com.ds.stock.database.tables.Providers;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.settings.SettingsManager;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import static com.ds.stock.database.DefaultTablesConstants.ID_ROW;

public class ProviderData extends Data{
    private String name, address, telephone;
    private long appliedInvoiceForPurchaseGoodId;

    public ProviderData(long id, String name, String address, String telephone, long appliedInvoiceForPurchaseGoodId) {
        super(id);
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.appliedInvoiceForPurchaseGoodId = appliedInvoiceForPurchaseGoodId;
    }

    public ProviderData(String name, String address, String telephone, long appliedInvoiceForPurchaseGoodId) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.appliedInvoiceForPurchaseGoodId = appliedInvoiceForPurchaseGoodId;
    }

    public static @Nullable @Unmodifiable ProviderData findProviderById(long id){
        try {
            String select = "SELECT * FROM " + Providers.TABLE_NAME + " WHERE " + ID_ROW + "=" + id;
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                resultSet.close();
                preparedStatement.close();

                return null;
            }

            ProviderData providerData = new ProviderData(resultSet.getLong(ID_ROW), resultSet.getString(Providers.NAME_ROW),
                    resultSet.getString(Providers.ADDRESS_ROW), resultSet.getString(Providers.TELEPHONE_ROW), resultSet.getLong(Providers.APPLIED_INVOICE_FOR_PURCHASED_GOOD_ID_ROW));
            resultSet.close();
            preparedStatement.close();

            return providerData;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public long getAppliedInvoiceForPurchaseGood() {
        return appliedInvoiceForPurchaseGoodId;
    }

    public void setAppliedInvoiceForPurchaseGoodId(long appliedInvoiceForPurchaseGoodId) {
        this.appliedInvoiceForPurchaseGoodId = appliedInvoiceForPurchaseGoodId;
    }
}
