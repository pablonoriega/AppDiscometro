package com.discometro.resources.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> getById(String id) throws Exception;

    List<T> getAll() throws Exception;

    boolean add(T t) throws Exception;

    boolean update(T t, String[] params) throws Exception;

    boolean delete(T t) throws Exception;
}
