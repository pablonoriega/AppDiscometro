package com.discometro;

import android.graphics.Bitmap;

public class Pair {

    private  String identificador;
    private Bitmap bitmap;

    public Pair(String identificador, Bitmap bitmap){
        this.bitmap=bitmap;
        this.identificador = identificador;

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
