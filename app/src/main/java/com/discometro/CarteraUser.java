package com.discometro;

import java.util.List;

public class CarteraUser {

    private List<User> listUser;

    public CarteraUser(List<User> listUser) {
        this.listUser = listUser;
    }

    public User find(String correo) {
        for (User u: listUser) {
            if (u.getCorreo().equals(correo)) {
                return u;
            }
        }
        return null;
    }
}
