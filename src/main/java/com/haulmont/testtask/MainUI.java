package com.haulmont.testtask;

import com.haulmont.testtask.UI.ClientView;
import com.haulmont.testtask.UI.CreditOfferView;
import com.haulmont.testtask.UI.CreditView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeUndefined();
        layout.setMargin(false);
        setContent(layout);
        TabSheet tabSheet = new TabSheet();
        TabSheet bankTabSheet = new TabSheet();
        try {
            bankTabSheet.addTab(new ClientView(), "Клиенты");
            bankTabSheet.addTab(new CreditView(), "Кредиты");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        layout.addComponent(tabSheet);
        tabSheet.addTab(bankTabSheet, "Банк");
        try {
            tabSheet.addTab(new CreditOfferView(), "Предложения по кредиту");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}