package com.discometro.Suscripciones;

public class SuscripcionesCardItem {

    private String usuario, usuarioid,nameDisco,fotoLogo;

    public SuscripcionesCardItem(String usuario, String usuarioid, String fotoLogo) {
        this.usuario = usuario;
        this.usuarioid = usuarioid;
        this.nameDisco=fotoLogo;
        this.fotoLogo=fotoLogo;
    }

    public String getFotoLogo() { return this.fotoLogo;}

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(String usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getNameDisco() {
        return nameDisco;
    }

    public void setNameDisco(String nameDisco) {
        this.nameDisco = nameDisco;
    }
}
