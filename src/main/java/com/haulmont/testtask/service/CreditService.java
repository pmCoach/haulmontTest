package com.haulmont.testtask.service;

import com.haulmont.testtask.backend.DAO.CreditDAO;
import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.hibernate.Factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditService {
    CreditDAO factory = Factory.getInstance().getCreditDAO();

    public void fillDB() throws SQLException{
            if (getAllCredits().isEmpty()) {
                addCredits().forEach(element -> {
                    try {
                        addCredit(element);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
            }

    }

    private List<Credit> addCredits() throws SQLException{
        List<Credit> credits = new ArrayList<>();
        credits.add(new Credit("Быстрый", 10000L, 25));
        credits.add(new Credit("Ипотечный", 10000000L, 7));
        credits.add(new Credit("Ускоренный", 25000L, 18));
        return credits;
    }

    public void addCredit(Credit credit) throws SQLException {
        factory.addCredit(credit);
    }
    public void updateCredit(Credit credit) throws SQLException{
        factory.updateCredit(credit);
    }
    public void deleteCredit(Credit credit) throws SQLException{
        factory.deleteCredit(credit);
    }

    public List getAllCredits() throws SQLException{
        return factory.getAllCredits();
    }

    public Credit getCreditById(String id) throws SQLException{
        return factory.getCreditById(id);
    }

    public List getCreditByName(String name) throws SQLException{
        if (name == null){
            return getAllCredits();
        } else
        return factory.getCreditsByName(name);
    }
}
