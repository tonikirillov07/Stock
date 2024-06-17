package com.ds.stock;

import com.ds.stock.data.dataUtils.writer.DataReader;

public class Test {
    public static void main(String[] args) {
        System.out.println(DataReader.getAllInvoice().get(0).getCustomerDataId());
    }
}
