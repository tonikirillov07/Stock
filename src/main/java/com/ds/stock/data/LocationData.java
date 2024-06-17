package com.ds.stock.data;

public class LocationData extends Data{
    private String name, address, telephone;

    public LocationData(long id, String name, String address, String telephone) {
        super(id);
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public LocationData(String name, String address, String telephone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
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
}
