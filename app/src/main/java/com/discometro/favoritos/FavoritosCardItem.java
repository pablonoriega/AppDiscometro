package com.discometro.favoritos;

public class FavoritosCardItem {

    private String usuario, usuarioid,nameDisco;

    public FavoritosCardItem(String usuario, String usuarioid, String nameDisco) {
        this.usuario = usuario;
        this.usuarioid = usuarioid;
        this.nameDisco=nameDisco;
    }

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
