package com.discometro.ObjetosPerdidos;

public class ObjetosPerdidosCardItem {

    private String nombreObj,usuario, descripcion, imagenLogo, imagenObjeto, nameDisco;
    //private Image imagen;

    public ObjetosPerdidosCardItem(String nombreObj, String usuario, String descripcion, String imagenLogo, String nameDisco,String imagenObjeto) {
        this.nombreObj = nombreObj;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.imagenLogo = imagenLogo;
        this.nameDisco=nameDisco;
        this.imagenObjeto=imagenObjeto;
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

    public String getImagenLogo() {
        return imagenLogo;
    }

    public void setNameDisco(String nameDisco){this.nameDisco = nameDisco;}

    public void setImagenLogo(String imagen) {
        this.imagenLogo = imagen;
    }

    public void setImagenObjeto(String imagenObjeto){this.imagenObjeto= imagenObjeto;}

    public String getImagenObjeto() {
        return imagenObjeto;
    }
}
