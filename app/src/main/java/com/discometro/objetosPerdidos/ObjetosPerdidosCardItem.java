package com.discometro.objetosPerdidos;

import android.media.Image;

public class ObjetosPerdidosCardItem {

    private String nombreObj,usuario, descripcion;
    private Image imagen;

    public ObjetosPerdidosCardItem(String nombreObj, String usuario, String descripcion, Image imagen) {
        this.nombreObj = nombreObj;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getNombreObj() {
        return nombreObj;
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

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
}
