package com.example.ejemplologin.Model;

public class Carro {

    private String LicensePlate;
    private String Color;
    private String Model;
    private String Brand;
    private String Year;
    private String Earning;
    private String Maintenance;

    public Carro() {
    }

    public String getLicensePlate() {
        return LicensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        LicensePlate = licensePlate;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getEarning() {
        return Earning;
    }

    public void setEarning(String earning) {
        Earning = earning;
    }

    public String getMaintenance() {
        return Maintenance;
    }

    public void setMaintenance(String maintenance) {
        Maintenance = maintenance;
    }
// Verificar esta parte.
    @Override
    public String toString() {
        return Model;
    }
}
