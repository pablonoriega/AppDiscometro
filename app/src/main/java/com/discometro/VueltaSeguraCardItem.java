package com.discometro;

public class VueltaSeguraCardItem {

    private String usuario, usuarioid, vehicle, location;

    public VueltaSeguraCardItem(String usuario, String usuarioid, String vehicle, String location) {
        this.usuario = usuario;
        this.usuarioid = usuarioid;
        this.vehicle = vehicle;
        this.location = location;
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

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
