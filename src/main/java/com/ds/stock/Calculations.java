package com.ds.stock;

import com.ds.stock.data.AppliedInvoiceForPurchaseGoodData;
import com.ds.stock.data.GoodData;
import org.jetbrains.annotations.NotNull;

public final class Calculations {
    public static double calculateCost(GoodData @NotNull [] goods){
        double purchaseSum = 0d;
        double saleSum = 0d;

        for (GoodData good : goods) {
            purchaseSum += good.getPurchaseCost();
            saleSum += good.getSaleCost();
        }

        return (purchaseSum - saleSum) / goods.length;
    }

    public static int getReserve(@NotNull AppliedInvoiceForPurchaseGoodData appliedInvoiceForPurchaseGoodData){
        return appliedInvoiceForPurchaseGoodData.getGoodsCount();
    }
}
