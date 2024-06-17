package com.ds.stock.data;

import com.ds.stock.data.dataUtils.writer.DataReader;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AppliedInvoiceForPurchaseGoodData extends Data{
    private long providerDataId;
    private String date;
    private GoodData[] goods;

    public AppliedInvoiceForPurchaseGoodData(long id, long providerDataId, String date, GoodData[] goods) {
        super(id);
        this.providerDataId = providerDataId;
        this.date = date;
        this.goods = goods;
    }

    public AppliedInvoiceForPurchaseGoodData(String date, long providerDataId, GoodData[] goods) {
        this.providerDataId = providerDataId;
        this.date = date;
        this.goods = goods;
    }

    public AppliedInvoiceForPurchaseGoodData(long id, String date, GoodData[] goods) {
        super(id);
        this.date = date;
        this.goods = goods;
    }

    public AppliedInvoiceForPurchaseGoodData(String date, GoodData[] goods) {
        this.date = date;
        this.goods = goods;
    }

    public static @Nullable AppliedInvoiceForPurchaseGoodData findAppliedInvoiceForPurchaseGoodDataById(long id){
        AppliedInvoiceForPurchaseGoodData appliedInvoiceForPurchaseGoodData = null;

        List<AppliedInvoiceForPurchaseGoodData> appliedInvoiceForPurchaseGoodDataList = DataReader.getAllAppliedInvoiceForPurchaseGoodData();

        assert appliedInvoiceForPurchaseGoodDataList != null;
        for (AppliedInvoiceForPurchaseGoodData invoiceForPurchaseGoodData : appliedInvoiceForPurchaseGoodDataList) {
            if(invoiceForPurchaseGoodData.getId() == id)
                return invoiceForPurchaseGoodData;
        }

        return null;
    }

    public long getProviderId() {
        return providerDataId;
    }

    public void setProviderData(long providerDataId) {
        this.providerDataId = providerDataId;
    }

    public String getDate() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public GoodData[] getGoods() {
        return goods;
    }

    public void setGoods(GoodData[] goods) {
        this.goods = goods;
    }

    public int getGoodsCount() {
        return goods.length;
    }
}
