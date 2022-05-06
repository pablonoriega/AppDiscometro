package com.discometro.objetosPerdidos;

import android.media.Image;

public class ObjetosPerdidosCardItem {

    private String nombreObj,usuario, descripcion, imagen, nameDisco;
    //private Image imagen;

    public ObjetosPerdidosCardItem(String nombreObj, String usuario, String descripcion, String imagen,String nameDisco) {
        this.nombreObj = nombreObj;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.nameDisco=nameDisco;
    }

    public String getNombreObj() {
        return nombreObj;
    }

    public String getNameDisco() {
        return nameDisco;
    }

    public void setNombreObj(String nombreObj) {
        this.nombreObj = nombreObj;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
