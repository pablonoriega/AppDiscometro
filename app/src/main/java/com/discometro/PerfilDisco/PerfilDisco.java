package com.discometro.PerfilDisco;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class PerfilDisco implements Parcelable {

    private String nameDisco;
    private String foto1;
    private String foto2;
    private String foto3;
    private String foto4;
    private String logo;
    private String banner;
    private String descripcion;
    private String latitud;
    private String longitud;
    private String correo;

    public PerfilDisco(String nameDisco, String logo, String foto1, String foto2, String foto3, String foto4, String correo, String banner, String descripcion, String latitud, String longitud) {

        this.nameDisco = nameDisco;
        this.logo = logo;
        this.foto1=foto1;
        this.foto2=foto2;
        this.foto3=foto3;
        this.foto4=foto4;
        this.correo=correo;
        this.banner=banner;
        this.descripcion=descripcion;
        this.longitud = longitud;
        this.latitud =latitud;
    }


    protected PerfilDisco(Parcel in) {
        nameDisco = in.readString();
        foto1 = in.readString();
        foto2 = in.readString();
        foto3 = in.readString();
        foto4 = in.readString();
        logo = in.readString();
        correo = in.readString();
        banner= in.readString();
        descripcion=in.readString();
        latitud= in.readString();
        longitud=in.readString();
    }

    public static final Creator<PerfilDisco> CREATOR = new Creator<PerfilDisco>() {
        @Override
        public PerfilDisco createFromParcel(Parcel in) {
            return new PerfilDisco(in);
        }

        @Override
        public PerfilDisco[] newArray(int size) {
            return new PerfilDisco[size];
        }
    };

    public String getBanner() {
        return banner;
    }

    public String getLatitud(){return latitud; }

    public String getLongitud(){return  longitud;}

    public String getDescripcion() {
        return descripcion;
    }

    public String getNameDisco() {
        return nameDisco;
    }

    public String getCorreo() {
        return correo;
    }

    public String getLogo() {
        return logo;
    }

    public String getFoto1() {
        return foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public String getFoto4() {
        return foto4;
    }

    public void setNameDisco(String nameDisco) {
        this.nameDisco = nameDisco;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nameDisco);
        parcel.writeString(foto1);
        parcel.writeString(foto2);
        parcel.writeString(foto3);
        parcel.writeString(foto4);
        parcel.writeString(logo);
        parcel.writeString(correo);
        parcel.writeString(banner);
        parcel.writeString(descripcion);
        parcel.writeString(latitud);
        parcel.writeString(longitud);

    }
}
