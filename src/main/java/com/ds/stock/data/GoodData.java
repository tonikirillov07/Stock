package com.ds.stock.data;

import com.ds.stock.Constants;
import com.ds.stock.database.DatabaseService;
import com.ds.stock.database.tables.Goods;
import com.ds.stock.utils.dialogs.ErrorDialog;
import com.ds.stock.utils.settings.SettingsManager;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import static com.ds.stock.database.DefaultTablesConstants.ID_ROW;

public class GoodData extends Data{
    private String name;
    public double purchaseCost, saleCost;
    private String unit;

    public GoodData(long id, String name, double purchaseCost, double saleCost, String unit) {
        super(id);
        this.name = name;
        this.purchaseCost = purchaseCost;
        this.saleCost = saleCost;
        this.unit = unit;
    }

    public GoodData(String name, double purchaseCost, double saleCost, String unit) {
        this.name = name;
        this.purchaseCost = purchaseCost;
        this.saleCost = saleCost;
        this.unit = unit;
    }

    public static @Nullable @Unmodifiable GoodData findGoodById(long id){
        try {
            String select = "SELECT * FROM " + Goods.TABLE_NAME + " WHERE " + ID_ROW + "=" + id;
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                resultSet.close();
                preparedStatement.close();

                return null;
            }

            GoodData goodData = new GoodData(resultSet.getLong(ID_ROW), resultSet.getString(Goods.NAME_ROW), resultSet.getDouble(Goods.PURCHASE_COST_ROW),
                    resultSet.getDouble(Goods.SALE_COST_ROW), resultSet.getString(Goods.UNIT_ROW));

            resultSet.close();
            preparedStatement.close();

            return goodData;
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

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public double getSaleCost() {
        return saleCost;
    }

    public void setSaleCost(double saleCost) {
        this.saleCost = saleCost;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
