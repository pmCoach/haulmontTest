package com.haulmont.testtask.backend.DAO;

import com.haulmont.testtask.backend.entities.CreditOffer;
import com.haulmont.testtask.backend.entities.PaymentGraphic;
import com.haulmont.testtask.backend.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PaymentGraphicDAOImpl implements PaymentGraphicDAO{
    @Override
    public void addPaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(paymentGraphic);
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
    public void updatePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(paymentGraphic);
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
    public List getAllPaymentGraphic() throws SQLException {
        Session session = null;
        List paymentGraphics = new ArrayList<PaymentGraphic>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            paymentGraphics = session.createQuery("SELECT p FROM PaymentGraphic p").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return paymentGraphics;
    }


    @Override
    public PaymentGraphic getPaymentGraphicById(String id) throws SQLException {
        Session session = null;
        PaymentGraphic paymentGraphic = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            paymentGraphic = (PaymentGraphic) session.load(PaymentGraphic.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return paymentGraphic;
    }

    @Override
    public void deletePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(paymentGraphic);
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
    public List getPaymentGraphicsByCreditOffer(CreditOffer creditOffer) throws SQLException {
        Session session = null;
        List paymentGraphics = new ArrayList<PaymentGraphic>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String creditOffer_id = creditOffer.getId();
            Query query = session.createQuery("SELECT graphic FROM PaymentGraphic graphic " +
                    "INNER JOIN graphic.creditOffer offer WHERE offer.id = :creditOffer_id").setParameter("creditOffer_id", creditOffer_id);
            paymentGraphics = (List<PaymentGraphic>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return paymentGraphics;
    }
}
