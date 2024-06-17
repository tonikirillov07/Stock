package com.ds.stock.data;

public class AppliedInvoiceForGoodSoldData extends Data{
    private CustomerData customerData;
    private String date;
    private GoodData[] goods;

    public AppliedInvoiceForGoodSoldData(long id, CustomerData customerData, String date, GoodData[] goods) {
        super(id);
        this.customerData = customerData;
        this.date = date;
        this.goods = goods;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
