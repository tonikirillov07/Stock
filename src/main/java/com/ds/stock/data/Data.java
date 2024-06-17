package com.ds.stock.data;

public abstract class Data {
    private long id;

    public Data() {}

    public Data(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
