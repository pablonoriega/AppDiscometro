package com.discometro;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

    private String correo;
    private String contra;
    private ArrayList<String> listFavoritos;


    public User(String correo, String contra) {
        this.correo = correo;
        this.contra = contra;
        this.listFavoritos= new ArrayList<>();
    }

    protected User(Parcel in) {
        correo = in.readString();
        contra = in.readString();
        listFavoritos = in.createStringArrayList();
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

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getContra() {
        return this.contra;
    }

    public ArrayList<String> getListFavoritos() {
        return listFavoritos;
    }
    public void añadirFavorito(String nameDisco){
        this.listFavoritos.add(nameDisco);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(correo);
        parcel.writeString(contra);
        parcel.writeStringList(listFavoritos);
    }
}
