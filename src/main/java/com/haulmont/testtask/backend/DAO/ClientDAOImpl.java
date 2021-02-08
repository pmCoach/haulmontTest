package com.haulmont.testtask.backend.DAO;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientDAOImpl implements  ClientDAO{
    @Override
    public void addClient(Client client) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(client);
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
    public void updateClient(Client client) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public Client getClientById(String id) throws SQLException {
        Session session = null;
        Client client = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            client = (Client) session.load(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return client;
    }

    @Override
    public List getAllClients() throws SQLException {
        Session session = null;
        List clients = new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clients = session.createQuery("SELECT client FROM Client client").list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return clients;
    }

    @Override
    public void deleteClient(Client client) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(client);
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
    public List getClientsByFIO(String client) throws SQLException {
        Session session = null;
        client.toLowerCase(Locale.ROOT);
        List<Client> clients = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clients = session.createQuery("SELECT client FROM Client client WHERE lower(client.name) like concat('%', :client, '%') OR lower(client.secname) like concat('%', :client, '%') OR lower(client.patronymic) like concat('%', :client, '%')").setParameter("client", client).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return clients;
    }
}
