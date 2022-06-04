package com.discometro.favoritos;

public class FavoritosCardItem {

    private String usuario, usuarioid,logoDisco;

    public FavoritosCardItem(String usuario, String usuarioid, String logoDisco) {
        this.usuario = usuario;
        this.usuarioid = usuarioid;
        this.logoDisco=logoDisco;
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

    public String getLogoDisco() {
        return logoDisco;
    }

    public void setLogoDisco(String logoDisco) {
        this.logoDisco = logoDisco;
    }


}
