package com.discometro.resources.service;

import android.widget.Toast;

import com.discometro.User;
import com.discometro.resources.dao.DAOUser;

import java.util.List;
import java.util.Optional;

public class DataService {

    private DAOUser daoUser;

    public DataService(AbstractFactoryData factory) {
        this.daoUser = factory.createDAOUser();
    }

    public Optional<User> getUserByEmail(String email) {
        try {
            return daoUser.getById(email);
        } catch (Exception e) {

        }
        return null;
    }

    public List<User> getAllUsers() throws Exception {
        return daoUser.getAll();
    }
}
