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
    private ArrayList<String> listFavoritos;
    private final DataBaseAdapter adapter= DataBaseAdapter.dataBaseAdapter;


    public User(String correo, String contra) {
        this.correo = correo;
        this.contra = contra;
        this.listFavoritos= new ArrayList<>();
        UUID uuid= UUID.randomUUID();
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
    public void eliminarFavorito(String nameDisco){
        listFavoritos.remove(nameDisco);
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
    public void saveUser(){
        Log.d("saveUser","saveUser-> saveDocument");
        adapter.saveDocumentWithFile(this.correo,this.contra,this.listFavoritos);
    }
}
