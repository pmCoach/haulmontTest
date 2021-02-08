package com.haulmont.testtask.backend.DAO;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.entities.CreditOffer;

import java.sql.SQLException;
import java.util.List;

public interface CreditOfferDAO {
    public void addCreditOffer(CreditOffer creditOffer) throws SQLException;
    public void updateCreditOffer(CreditOffer creditOffer) throws SQLException;
    public List getAllCreditOffers() throws SQLException;
    public CreditOffer getCreditOfferById(String id) throws SQLException;
    public void deleteCreditOffer(CreditOffer creditOffer) throws SQLException;
    public List getCreditOffersByClient(Client client) throws SQLException;
    public List getCreditOffersByCredit(Credit credit) throws SQLException;
}
