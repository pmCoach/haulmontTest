package com.haulmont.testtask.backend.DAO;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDAOImpl implements CreditDAO{
    @Override
    public void addCredit(Credit client) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        } catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Ошибка 'addCredit'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public void updateCredit(Credit credit) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(credit);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List getAllCredits() throws SQLException {
        List credits = new ArrayList<Credit>();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            credits = session.createQuery("SELECT credit FROM Credit credit").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return credits;
    }

    @Override
    public Credit getCreditById(String id) throws SQLException {
        Credit credit = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            credit = (Credit) session.load(Credit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen());
        }
        return credit;
    }

    @Override
    public void deleteCredit(Credit credit) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(credit);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List getCreditsByClient(Client client) throws SQLException {
        return null;
    }

    @Override
    public List getCreditsByName(String name) throws SQLException {
        List<Credit> credits = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            credits = session.createQuery("SELECT credit FROM Credit credit WHERE lower(credit.creditName) like lower(concat('%', :creditName, '%'").setParameter("creditName", name).list();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
