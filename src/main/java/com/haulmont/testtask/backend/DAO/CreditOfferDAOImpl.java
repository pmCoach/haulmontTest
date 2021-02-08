package com.haulmont.testtask.backend.DAO;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.entities.CreditOffer;
import com.haulmont.testtask.backend.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditOfferDAOImpl implements CreditOfferDAO{
    @Override
    public void addCreditOffer(CreditOffer creditOffer) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(creditOffer);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Ошибка 'addCreditOffer'", JOptionPane.OK_OPTION);
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }

    }

    @Override
    public void updateCreditOffer(CreditOffer creditOffer) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(creditOffer);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Ошибка 'updateCreditOffer'", JOptionPane.OK_OPTION);
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List getAllCreditOffers() throws SQLException {
        Session session = null;
        List creditOffers = new ArrayList<CreditOffer>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            creditOffers = session.createQuery("SELECT offer FROM CreditOffer offer").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return creditOffers;
    }

    @Override
    public CreditOffer getCreditOfferById(String id) throws SQLException {
        CreditOffer creditOffer = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            creditOffer = (CreditOffer) session.load(CreditOffer.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return creditOffer;
    }

    @Override
    public void deleteCreditOffer(CreditOffer creditOffer) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(creditOffer);
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
    public List getCreditOffersByClient(Client client) throws SQLException {
        List creditOffers = new ArrayList<CreditOffer>();
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String client_id = client.getId();
            Query query = session.createQuery("SELECT offer FROM CreditOffer offer" +
                    " INNER JOIN offer.client client WHERE client.id = :client_id").setParameter("client_id", client_id);
            creditOffers = (List<CreditOffer>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return creditOffers;
    }

    @Override
    public List getCreditOffersByCredit(Credit credit) throws SQLException {
        List creditOffers = new ArrayList<CreditOffer>();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String credit_id = credit.getId();
            Query query = session.createQuery("SELECT offer FROM CreditOffer offer" +
                    " INNER JOIN offer.credit credit WHERE credit.id = :credit_id").setParameter("credit_id", credit_id);
            creditOffers = (List<CreditOffer>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return creditOffers;
    }
}
