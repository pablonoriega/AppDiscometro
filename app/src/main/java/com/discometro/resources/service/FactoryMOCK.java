package com.discometro.resources.service;

import com.discometro.resources.dao.DAOUser;
import com.discometro.resources.dao.MOCK.DAOUserMock;

public class FactoryMOCK implements AbstractFactoryData {

    @Override
    public DAOUser createDAOUser() {
        return new DAOUserMock();
    }
}
