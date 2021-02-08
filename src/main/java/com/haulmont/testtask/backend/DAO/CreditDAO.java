package com.haulmont.testtask.backend.DAO;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.entities.Credit;

import java.sql.SQLException;
import java.util.List;

public interface CreditDAO {
    public void addCredit(Credit credit) throws SQLException;
    public void updateCredit(Credit credit) throws SQLException;
    public List getAllCredits() throws SQLException;
    public Credit getCreditById(String id) throws SQLException;
    public void deleteCredit(Credit credit) throws SQLException;
    public List getCreditsByClient(Client client) throws SQLException;
    public List getCreditsByName(String name) throws SQLException;
}
