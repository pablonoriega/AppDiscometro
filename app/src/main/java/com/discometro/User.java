package com.discometro;

public class User {

    private String correo;
    private String contra;

    public User(String correo, String contra) {
        this.correo = correo;
        this.contra = contra;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getContra() {
        return this.contra;
    }
}
