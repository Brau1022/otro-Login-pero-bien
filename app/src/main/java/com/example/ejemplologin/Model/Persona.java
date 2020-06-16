package com.example.ejemplologin.Model;

import android.widget.ImageView;

public class Persona {
    private String Uid;
    private String Nombre;
    private String Apellido;
    private String Cedula;
    private String Edad;
    private String Payment;
    private String Location;
    private String CarLicensePlate;
    private String WeeklyCheking;
    private ImageView ProfilePicture;

    public Persona() {
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public ImageView getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(ImageView profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getCarLicensePlate() {
        return CarLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        CarLicensePlate = carLicensePlate;
    }

    public String getWeeklyCheking() {
        return WeeklyCheking;
    }

    public void setWeeklyCheking(String weeklyCheking) {
        WeeklyCheking = weeklyCheking;
    }
// Verificar esta parte.
    @Override
    public String toString() {
        return Nombre;
    }
}
