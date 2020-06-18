package com.example.ejemplologin.Model;

public class DataSetFire {
    String nombre;
    String apellido;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public DataSetFire() {
    }

    public DataSetFire(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
