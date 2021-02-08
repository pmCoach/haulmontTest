package com.haulmont.testtask.UI;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.backend.entities.CreditOffer;
import com.haulmont.testtask.backend.entities.PaymentGraphic;
import com.haulmont.testtask.service.ClientService;
import com.haulmont.testtask.service.CreditOfferService;
import com.haulmont.testtask.service.CreditService;
import com.haulmont.testtask.service.PaymentGraphicService;
import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditOfferEditUI extends VerticalLayout {
    private TextField creditSum = new TextField("Сумма кредита");
    private TextField monthsOfCredit = new TextField("Срок выплаты в месяцах");
    private ComboBox<Client> clientSelect = new ComboBox<>("Клиент");
    private ComboBox<Credit> creditSelect = new ComboBox<>("Кредит");
    private Label itog = new Label("");
    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Button addPaymentGraphic = new Button("Сформировать график платежей");
    private Button showPaymentGraphic = new Button("Просмотреть график платежей");
    private Button calculateSum = new Button("Рассчитать итоговую сумму кредита");
    private CreditOffer creditOffer;
    private ClientService clientService = new ClientService();
    private CreditService creditService = new CreditService();
    private CreditOfferService creditOfferService = new CreditOfferService();
    private PaymentGraphicService paymentGraphicService = new PaymentGraphicService();
    private CreditOfferView creditOfferView;
    private Binder<CreditOffer> binder = new Binder();

    public CreditOfferEditUI(CreditOffer creditOffer, CreditOfferView creditOfferView) throws SQLException{
        setVisible(false);
        if (creditOffer == null) {
            this.creditOffer = new CreditOffer();
        } else {
            this.creditOffer = creditOffer;
        }
        this.creditOfferView = creditOfferView;
        updateSelects();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout paymentLayout = new HorizontalLayout();
        addClickListeners();
        layout.addComponents(add, update, delete, calculateSum, cancel);
        paymentLayout.addComponents(addPaymentGraphic, showPaymentGraphic);
        addComponents(creditSum, monthsOfCredit, clientSelect, creditSelect, itog, layout, paymentLayout);
    }


    public void editConfigure(CreditOffer creditOffer){
        setVisible(true);
        try{
            updateSelects();
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        if (creditOffer == null){
            clear();
            update.setVisible(false);
            delete.setVisible(false);
            addPaymentGraphic.setVisible(false);
            showPaymentGraphic.setVisible(false);
            itog.setVisible(false);
            calculateSum.setVisible(true);
            add.setVisible(true);
            creditOfferView.paymentGrid.setVisible(false);
        } else {
            this.creditOffer = creditOffer;
            setCreditOffer(creditOffer);
            update.setVisible(true);
            delete.setVisible(true);
            addPaymentGraphic.setVisible(true);
            showPaymentGraphic.setVisible(true);
            add.setVisible(false);
            calculateSum.setVisible(false);
            itog.setVisible(true);
            creditOfferView.paymentGrid.setVisible(false);
            calculateItog();
        }
        binder.forField(monthsOfCredit).withConverter(new StringToLongConverter("Поле введено неверно"))
                .bind(CreditOffer::getMonthsOfCredit,CreditOffer::setMonthsOfCredit);
        creditSum.setPlaceholder("Введите сумму кредита");
        monthsOfCredit.setPlaceholder("Введите срок выплаты");
    }

    private void updateSelects() throws SQLException{
        clientSelect.setItems(clientService.getAllClients());
        clientSelect.setItemCaptionGenerator(Client::getInitials);
        creditSelect.setItems(creditService.getAllCredits());
        creditSelect.setItemCaptionGenerator(Credit::getCreditName);
        creditSelect.addValueChangeListener(valueChangeEvent -> {
            if (creditSelect.getValue() == null)
                binder.forField(creditSum).withConverter(new StringToLongConverter("Поле введено неверно"))
                        .bind(CreditOffer::getCreditSum, CreditOffer::setCreditSum);
            else
                binder.forField(creditSum).withConverter(new StringToLongConverter("Поле введено неверно"))
                    .withValidator(event -> event <= creditSelect.getValue().getCreditLimit(), "Сумма кредита не должна быть выше " + creditSelect.getValue().getCreditLimit())
                    .bind(CreditOffer::getCreditSum, CreditOffer::setCreditSum);
        });

    }

    private void addClickListeners(){
        calculateSum.addClickListener(clickEvent -> calculateItog());

        cancel.addClickListener(event -> {
            this.setVisible(false);
        });
        add.addClickListener(clickEvent -> {
            try {
                if (fieldCheck()){
                    creditOfferService.addCreditOffer(getCreditOffer());
                    creditOfferView.updateGrid();
                    this.setVisible(false);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                updateCreditOffer();
                creditOfferView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        delete.addClickListener(event -> {
            try {
                creditOfferService.deleteCreditOffer(creditOffer);
                creditOfferView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        addPaymentGraphic.addClickListener(event ->
        {
            try {
                if(paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer).isEmpty())
                    createPaymentGraphic();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        showPaymentGraphic.addClickListener(event -> {
            try {
                creditOfferView.paymentGridConfigure(paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck(){
        if (creditSum.isEmpty() || monthsOfCredit.isEmpty() || clientSelect.getValue() == null || creditSelect.getValue() == null)
        {
            add.setComponentError(new UserError("Не все поля введены"));
            return false;
        }
        add.setComponentError(null);
        return true;
    }

    private void calculateItog(){
        Long creditSum;
        Integer percents;
        try{
            creditSum = Long.parseLong(this.creditSum.getValue());
            percents = creditSelect.getValue().getPercent();
            Long creditBody = creditSum/100*(100 + percents);
            itog.setValue("Итоговая сумма с учетом процентов: " + creditBody + " ,  Сумма процентов: " + (creditBody - creditSum));
        } catch (Exception ex){
            System.out.println("error");
        }
        itog.setVisible(true);
    }

    private void createPaymentGraphic(){
        List<PaymentGraphic> paymentGraphicList = new ArrayList<>();
        Long ostatok;
        ostatok = creditOffer.getCreditSum()/100*(100 + creditOffer.getCredit().getPercent());
        for(Long i = creditOffer.getMonthsOfCredit(), month = 0L; i > 0; i--, month++){
            PaymentGraphic paymentGraphic = new PaymentGraphic();
            try {
                paymentGraphicList = paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (i != creditOffer.getMonthsOfCredit()){
            } else {
                paymentGraphic.setOstatok(ostatok);
            }

            paymentGraphic.setDate(LocalDate.now().plusMonths(month));
            paymentGraphic.setPaymentSum(ostatok/i);
            paymentGraphic.setCreditPercents(paymentGraphic.getPaymentSum()/100*creditOffer.getCredit().getPercent());
            paymentGraphic.setCreditBody(paymentGraphic.getPaymentSum()-paymentGraphic.getCreditPercents());
            paymentGraphic.setCreditOffer(creditOffer);
            paymentGraphic.setOstatok(ostatok - paymentGraphic.getPaymentSum());
            ostatok -= paymentGraphic.getPaymentSum();
            try {
                System.out.println("Чтото пошло не так");
                paymentGraphicService.addPaymentGraphic(paymentGraphic);
            } catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }


    private void addCreditOffer() throws SQLException{
        creditOffer = getCreditOffer();
        creditOfferService.addCreditOffer(creditOffer);
    }

    private void updateCreditOffer() throws SQLException{
        this.creditOffer = getCreditOffer();
        creditOfferService.updateCreditOffer(creditOffer);
    }

    private void clear(){
        clientSelect.clear();
        creditSelect.clear();
        creditSum.clear();
        monthsOfCredit.clear();
        itog.setValue("");
    }

    private CreditOffer getCreditOffer(){
        creditOffer.setCreditSum(Long.parseLong(creditSum.getValue()));
        creditOffer.setClient(clientSelect.getValue());
        creditOffer.setCredit(creditSelect.getValue());
        creditOffer.setMonthsOfCredit(Long.parseLong(monthsOfCredit.getValue()));
        return creditOffer;
    }

    private void setCreditOffer(CreditOffer creditOffer){
        clientSelect.setSelectedItem(creditOffer.getClient());
        creditSelect.setSelectedItem(creditOffer.getCredit());
        creditSum.setValue(creditOffer.getCreditSum().toString());
        monthsOfCredit.setValue(creditOffer.getMonthsOfCredit().toString());
    }
}
