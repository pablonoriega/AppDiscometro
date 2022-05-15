package com.discometro;

import com.discometro.PerfilDisco.PerfilDisco;

import java.util.ArrayList;

public class AllUsers {

    private ArrayList<User> arrayUsers;
    private static AllUsers allUsers;


    private  AllUsers(){
        arrayUsers = new ArrayList<>();
    }

    public static AllUsers getInstance(){
        if (allUsers == null){
            allUsers = new AllUsers();
        }
        return allUsers;
    }

    public void setAllUsers(ArrayList<User> allDiscos) {
        this.arrayUsers = allDiscos;
    }

    public ArrayList<User> getAllUsers() {
        return arrayUsers;
    }
}
