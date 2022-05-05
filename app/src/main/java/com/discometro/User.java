package com.discometro;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.discometro.dataBase.DataBaseAdapter;

import java.util.ArrayList;
import java.util.UUID;

public class User implements Parcelable {

    private String correo;
    private String contra;
    private String birthday;
    private String surname;
    private String dni;
    private String name;
    private ArrayList<String> listFavoritos;
    private String url;



    public User(String correo, String contra, String name, String birthday, String surname , String dni, ArrayList<String> listFavoritos, String url) {
        this.correo = correo;
        this.contra = contra;
        this.birthday=birthday;
        this.surname=surname;
        this.dni=dni;
        this.name=name;
        this.listFavoritos = listFavoritos;
        this.url=url;


    }


    protected User(Parcel in) {
        correo = in.readString();
        contra = in.readString();
        birthday = in.readString();
        surname = in.readString();
        dni = in.readString();
        name = in.readString();
        listFavoritos = in.createStringArrayList();
        url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(correo);
        parcel.writeString(contra);
        parcel.writeString(birthday);
        parcel.writeString(surname);
        parcel.writeString(dni);
        parcel.writeString(name);
        parcel.writeStringList(listFavoritos);
        parcel.writeString(url);
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
    public String getUrl() { return url; }


    public void añadirFavorito(String nameDisco){
        this.listFavoritos.add(nameDisco);
    }
    public void eliminarFavorito(String nameDisco){
        listFavoritos.remove(nameDisco);
    }



}
