package com.discometro.Suscripciones;

public class SuscripcionesCardItem {

    private String usuario, usuarioid,fotoLogo;

    public SuscripcionesCardItem(String usuario, String usuarioid, String fotoLogo) {
        this.usuario = usuario;
        this.usuarioid = usuarioid;
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


}
