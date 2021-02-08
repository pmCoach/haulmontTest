package com.haulmont.testtask.service;

import com.haulmont.testtask.backend.DAO.PaymentGraphicDAO;
import com.haulmont.testtask.backend.entities.CreditOffer;
import com.haulmont.testtask.backend.entities.PaymentGraphic;
import com.haulmont.testtask.backend.hibernate.Factory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentGraphicService {
    PaymentGraphicDAO factory = Factory.getInstance().getPaymentGraphicDAO();

    public void addPaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        factory.addPaymentGraphic(paymentGraphic);
    }
    public void updatePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException{
        factory.updatePaymentGraphic(paymentGraphic);
    }
    public void deletePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException{
        factory.deletePaymentGraphic(paymentGraphic);
    }
    public List getAllPaymentGraphics() throws SQLException{
        return factory.getAllPaymentGraphic();
    }
    public PaymentGraphic getPaymentGraphicById(String id) throws SQLException{
        return factory.getPaymentGraphicById(id);
    }
    public List getPaymentGraphicByCreditOffer(CreditOffer creditOffer) throws SQLException{
        if (creditOffer == null){
            return getAllPaymentGraphics();
        } else {
            return factory.getPaymentGraphicsByCreditOffer(creditOffer);
        }
    }
}
