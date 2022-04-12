package com.discometro.resources.dao.MOCK;

import com.discometro.User;
import com.discometro.resources.dao.DAOUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DAOUserMock implements DAOUser {

    private Map<String, User> listUser = new HashMap<>();

    public DAOUserMock() {
        listUser.put("a@gmail.com", new User("a@gmail.com", "adri123"));
        listUser.put("p@gmail.com", new User("p@gmail.com", "pablo123"));
        listUser.put("c@gmail.com", new User("c@gmail.com", "christian123"));
    }


    @Override
    public Optional<User> getById(String id) throws Exception {
        return Optional.ofNullable(listUser.get(id));
    }

    @Override
    public List<User> getAll() throws Exception {
        return new ArrayList<>(listUser.values());
    }

    @Override
    public boolean add(User user) throws Exception {
        if (listUser.containsKey(user.getCorreo())) {
            return false;
        }
        listUser.put(user.getCorreo(), user);
        return true;
    }

    @Override
    public boolean update(User user, String[] params) throws Exception {
        user.setCorreo(Objects.requireNonNull(params[0], "Correo no puede ser nulo"));
        user.setContra(Objects.requireNonNull(params[1], "Contraseña no puede ser nula"));
        return listUser.replace(user.getCorreo(), user) != null;
    }

    @Override
    public boolean delete(User user) throws Exception {
        return listUser.remove(user.getCorreo()) != null;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws Exception {
        if (getById(email).isPresent()) {
            User u = listUser.get(email);
            if (u.getContra().equals(password)) {
                return u;
            }
            else {
                throw new Exception();
            }
        }
        else {
            throw new Exception();
        }
    }
}
