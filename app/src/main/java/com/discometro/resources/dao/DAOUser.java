package com.discometro.resources.dao;

import com.discometro.User;

public interface DAOUser extends DAO<User> {

    User findUserByEmailAndPassword(String email, String password) throws Exception;
}
