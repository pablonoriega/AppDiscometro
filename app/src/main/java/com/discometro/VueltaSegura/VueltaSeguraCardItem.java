package com.discometro.VueltaSegura;

import com.discometro.User;

import java.util.ArrayList;

public class VueltaSeguraCardItem {

    private String name, usuarioid, vehicle, location,number,origen,fotoLogo;
    private ArrayList<String> userList;

    public VueltaSeguraCardItem(String name, String usuarioid, String vehicle, String location, String number, String origen,String fotoLogo, ArrayList<String> userList) {
        this.name = name;
        this.usuarioid = usuarioid;
        this.vehicle = vehicle;
        this.location = location;
        this.number=number;
        this.origen=origen;
        this.fotoLogo=fotoLogo;
        this.userList = userList;
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

    public ArrayList<String> getUserList() { return this.userList; }

    public void setUserList(ArrayList<String> userList) { this.userList = userList; }

    public boolean afegirUserToList(User u) {
        if (!listFull()) {
            this.userList.add(u.getCorreo());
            return true;
        }
        return false;
    }

    private boolean listFull() {
        if (userList.size() == Integer.parseInt(number)) {
            return true;
        }
        return false;
    }
}
