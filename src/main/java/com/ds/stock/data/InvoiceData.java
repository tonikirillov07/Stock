package com.ds.stock.data;

import com.ds.stock.data.dataUtils.writer.DataReader;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class InvoiceData extends Data{
    private long customerDataId;
    private String date;
    private String goods;

    public InvoiceData(long id, long customerDataId, String date, String goods) {
        super(id);
        this.customerDataId = customerDataId;
        this.date = date;
        this.goods = goods;
    }

    public InvoiceData(long customerDataId, String date, String goods) {
        this.customerDataId = customerDataId;
        this.date = date;
        this.goods = goods;
    }

    public static InvoiceData findInvoiceDataById(long id){
        AtomicReference<InvoiceData> invoiceData = new AtomicReference<>();

        Objects.requireNonNull(DataReader.getAllInvoice()).forEach(invoiceData1 -> {
            if(invoiceData1.getId() == id){
                invoiceData.set(invoiceData1);
            }
        });

        return invoiceData.get();
    }

    public long getCustomerDataId() {
        return customerDataId;
    }

    public void setCustomerData(long customerDataId) {
        this.customerDataId = customerDataId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }


    public int getGoodsCount() {
        return 0;
    }
}
