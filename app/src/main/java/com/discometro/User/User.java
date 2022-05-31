package com.discometro.User;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User  {

    private String correo;
    private String contra;
    private String birthday;
    private String surname;
    private String dni;
    private String name;
    private ArrayList<String> listFavoritos;
    private ArrayList<String> listSuscripciones;
    private String uri;



    public User(String correo, String contra, String name, String birthday, String surname , String dni, ArrayList<String> listFavoritos, String uri, ArrayList<String> listSuscripciones) {
        this.correo = correo;
        this.contra = contra;
        this.birthday=birthday;
        this.surname=surname;
        this.dni=dni;
        this.name=name;
        this.listFavoritos = listFavoritos;
        this.uri=uri;
        this.listSuscripciones = listSuscripciones;


    }



    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setContra(String contra) {
        this.contra = contra;
    }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setDni(String dni) { this.dni = dni; }
    public void setName(String name) { this.name = name; }
    public void setListFavoritos(ArrayList<String> listFavoritos) { this.listFavoritos = listFavoritos; }
    public void setUri(String uri){this.uri=uri;}
    public void setListSuscripciones(ArrayList<String> listSuscripciones) {this.listSuscripciones = listSuscripciones; }

    public ArrayList<String> getListSuscripciones() {return listSuscripciones; }
    public String getCorreo() {
        return this.correo;
    }
    public String getContra() { return this.contra; }
    public ArrayList<String> getListFavoritos() {
        return listFavoritos;
    }
    public String getBirthday() { return birthday; }
    public String getDni() { return dni; }
    public String getSurname() { return surname; }
    public String getName() { return name; }
    public String getUrl() {
        return uri; }


    public void añadirFavorito(String nameDisco){
        this.listFavoritos.add(nameDisco);
    }
    public void eliminarFavorito(String nameDisco){
        listFavoritos.remove(nameDisco);
    }

    public void añadirSuscripcion(String nameDisco){
        this.listSuscripciones.add(nameDisco);
    }
    public void eliminarSuscripcion(String nameDisco){
        this.listSuscripciones.remove(nameDisco);
    }



}
