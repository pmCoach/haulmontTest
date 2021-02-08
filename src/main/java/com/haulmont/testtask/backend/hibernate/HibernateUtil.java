package com.haulmont.testtask.backend.hibernate;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.entities.CreditOffer;
import com.haulmont.testtask.backend.entities.PaymentGraphic;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().
                    addAnnotatedClass(Client.class).
                    addAnnotatedClass(Credit.class).
                    addAnnotatedClass(CreditOffer.class).
                    addAnnotatedClass(PaymentGraphic.class).buildSessionFactory();
        } catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
