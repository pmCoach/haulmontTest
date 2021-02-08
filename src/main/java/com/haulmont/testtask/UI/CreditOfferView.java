package com.haulmont.testtask.UI;

import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.entities.CreditOffer;
import com.haulmont.testtask.backend.entities.PaymentGraphic;
import com.haulmont.testtask.service.CreditOfferService;
import com.haulmont.testtask.service.CreditService;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;


import java.sql.SQLException;
import java.util.List;

public class CreditOfferView extends VerticalLayout {
    private Grid<CreditOffer> creditGrid = new Grid<>(CreditOffer.class);
    public Grid<PaymentGraphic> paymentGrid = new Grid<>(PaymentGraphic.class);
    private Button add = new Button("Add");
    private CreditOfferService creditOfferService = new CreditOfferService();
    private CreditOfferEditUI addUI = new CreditOfferEditUI(new CreditOffer(), this);


    public CreditOfferView () throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(creditGrid, addUI);
        paymentGrid.setVisible(false);
        layout.addComponents(add);
        add.addClickListener(clickEvent -> {addUI.setVisible(true); addUI.editConfigure(null);});
        addComponents(layout, gridLayout, paymentGrid);
    }



    protected void updateList() throws SQLException{
        List<CreditOffer> creditOffers = creditOfferService.getCreditOfferByCredit(null);
        creditGrid.setItems(creditOffers);
    }

    public void updateGrid() throws SQLException{
        creditGrid.setItems(creditOfferService.getAllCreditOffers());
    }

    public void paymentGridConfigure(List<PaymentGraphic> paymentGraphics) throws SQLException{
        paymentGrid.setWidth("800");
        paymentGrid.setVisible(true);
        paymentGrid.setItems(paymentGraphics);
        paymentGrid.removeColumn("id");
        paymentGrid.removeColumn("date");
        paymentGrid.removeColumn("creditOffer");
        paymentGrid.removeColumn("origDate");
        paymentGrid.setColumns("paymentSum", "creditBody", "creditPercents", "ostatok");
        paymentGrid.addColumn(column -> column.getOrigDate().toString()).setCaption("Date");
    }


    private void gridConfigure() throws SQLException {
        creditGrid.setWidth("800");
        creditGrid.removeColumn("client");
        creditGrid.removeColumn("credit");
        creditGrid.setColumns("creditSum", "monthsOfCredit");
        creditGrid.addColumn(client -> client.getClient().getInitials()).setCaption("Client");
        creditGrid.addColumn(credit -> credit.getCredit().getCreditName()).setCaption("Credit");
        List<CreditOffer> credits = creditOfferService.getAllCreditOffers();
        creditGrid.setItems(credits);
        creditGrid.asSingleSelect().addSingleSelectionListener(event -> addUI.editConfigure(event.getValue()));
    }
}
