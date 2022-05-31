package com.discometro.VueltaSegura;

public class VueltaSeguraCardItem {

    private String name, usuarioid, vehicle, location,number,origen,fotoLogo;


    public VueltaSeguraCardItem(String name, String usuarioid, String vehicle, String location, String number, String origen, String fotoLogo) {
        this.name = name;
        this.usuarioid = usuarioid;
        this.vehicle = vehicle;
        this.location = location;
        this.number=number;
        this.origen=origen;
        this.fotoLogo=fotoLogo;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getFotoLogo() {
        return fotoLogo;
    }

    public String getOrigen(){
        return this.origen;
    }

    public void setUsuario(String usuario) {
        this.name = usuario;
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
