package com.discometro;

import com.discometro.PerfilDisco.PerfilDisco;

import java.util.ArrayList;

public class AllDiscos {
    private ArrayList<PerfilDisco> arrayDiscos;
    private static AllDiscos allDiscos;


    private  AllDiscos(){
        arrayDiscos = new ArrayList<>();
    }
    public static AllDiscos getInstance(){
        if (allDiscos == null){
            allDiscos = new AllDiscos();
        }
        return allDiscos;
    }

    public void setAllDiscos(ArrayList<PerfilDisco> allDiscos) {
        this.arrayDiscos = allDiscos;
    }

    public ArrayList<PerfilDisco> getAllDiscos() {
        return arrayDiscos;
    }
}
