package com.haulmont.testtask.service;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.hibernate.Factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ClientService {
    private Factory factory = Factory.getInstance();

    public void fillClientTable() throws SQLException{

        if (getAllClients().isEmpty()) {
            List<Client> clients = addClients();
            clients.forEach(client -> {
                try {
                    factory.getClientDAO().addClient(client);
                } catch (SQLException ex){
                    System.out.println("Cannot fill the Client Table");
                    ex.printStackTrace();
                } });
        }


    }

    private List<Client> addClients(){
        List<Client> clients = new ArrayList<Client>();
        clients.add(new Client("Alexey", "Scvorcov", "Vladimirovich", "89178139389", "alexey_scvorcov@mail.ru", "4534 345869" ));
        clients.add(new Client("Aleksandr", "Komolov", "Vitalievich", "89171454312", "aleksandr132@mail.ru", "5242 134869"));
        clients.add(new Client("Viktoria", "Samoilova", "Valirievna", "9178261523", "vika_samoil@mail.ru", "2518 039145"));
        return clients;
    }

    public void addClient(Client client) throws SQLException {
        factory.getClientDAO().addClient(client);
    }

    public void updateClient(Client client) throws SQLException {
        factory.getClientDAO().updateClient(client);
    }

    public void deleteClient(Client client) throws SQLException {
        factory.getClientDAO().deleteClient(client);
    }

    public List getAllClients() throws SQLException {
        return factory.getClientDAO().getAllClients();
    }

    public Client getClientById(String id) throws SQLException {
        return factory.getClientDAO().getClientById(id);
    }

    public List getClientsByFIO(String fio) throws SQLException {
        if (fio == null){
            return getAllClients();
        } else return factory.getClientDAO().getClientsByFIO(fio);
    }
}
