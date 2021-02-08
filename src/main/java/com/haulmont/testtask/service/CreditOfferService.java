package com.haulmont.testtask.service;

import com.haulmont.testtask.backend.DAO.CreditOfferDAO;
import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.entities.CreditOffer;
import com.haulmont.testtask.backend.hibernate.Factory;

import java.sql.SQLException;
import java.util.List;

public class CreditOfferService {
    private CreditOfferDAO factory = Factory.getInstance().getCreditOfferDAO();

    public void addCreditOffer(CreditOffer creditOffer) throws SQLException {
        factory.addCreditOffer(creditOffer);
        System.out.println("Credit sum" + creditOffer.getCreditSum());
        System.out.println("Credit_id" + creditOffer.getCredit().getId());
        System.out.println("Client_id" + creditOffer.getClient().getId());
    }
    public void updateCreditOffer(CreditOffer creditOffer) throws SQLException{
        factory.updateCreditOffer(creditOffer);
    }
    public void deleteCreditOffer(CreditOffer creditOffer) throws SQLException{
        factory.deleteCreditOffer(creditOffer);
    }
    public List getAllCreditOffers() throws SQLException{
        return factory.getAllCreditOffers();
    }
    public CreditOffer getCreditOfferById(String id) throws SQLException{
        return factory.getCreditOfferById(id);
    }
    public List getCreditOfferByClient(Client client) throws SQLException{
        return factory.getCreditOffersByClient(client);
    }
    public List getCreditOfferByCredit(Credit credit) throws SQLException{
        return factory.getCreditOffersByCredit(credit);
    }
}
