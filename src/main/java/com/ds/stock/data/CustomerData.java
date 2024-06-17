package com.ds.stock.data;

import com.ds.stock.Constants;
import com.ds.stock.data.dataUtils.writer.DataReader;
import com.ds.stock.database.DatabaseService;
import com.ds.stock.database.tables.Customers;
import com.ds.stock.database.tables.Goods;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.settings.SettingsManager;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

import static com.ds.stock.database.DefaultTablesConstants.ID_ROW;

public class CustomerData extends Data{
    private String name, address, telephone;
    private long invoiceDataId;

    public CustomerData(long id, String name, String address, String telephone) {
        super(id);
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public CustomerData(String name, String address, String telephone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public CustomerData(String name, String address, String telephone, long invoiceDataId) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.invoiceDataId = invoiceDataId;
    }

    public CustomerData(long id, String name, String address, String telephone, long invoiceDataId) {
        super(id);
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.invoiceDataId = invoiceDataId;
    }

    public static boolean findCustomerByName(String name){
        List<CustomerData> customerData = DataReader.getAllCustomers();

        assert customerData != null;
        for (CustomerData customer : customerData) {
            if(customer.getName().equals(name)){
                return true;
            }
        }

        return false;
    }

    public static @Nullable @Unmodifiable CustomerData findCustomerById(long id){
        try {
            String select = "SELECT * FROM " + Customers.TABLE_NAME + " WHERE " + ID_ROW + "=" + id;
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                resultSet.close();
                preparedStatement.close();

                return null;
            }

            CustomerData customerData = new CustomerData(resultSet.getLong(ID_ROW), resultSet.getString(Customers.NAME_ROW),
                    resultSet.getString(Customers.ADDRESS_ROW), resultSet.getString(Customers.TELEPHONE_ROW), resultSet.getLong(Customers.INVOICE_ID_ROW));

            resultSet.close();
            preparedStatement.close();

            return customerData;
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

    public long getInvoiceData() {
        return invoiceDataId;
    }

    public void setInvoiceData(long invoiceDataId) {
        this.invoiceDataId = invoiceDataId;
    }
}
