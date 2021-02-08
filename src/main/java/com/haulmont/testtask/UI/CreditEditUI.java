package com.haulmont.testtask.UI;

import com.haulmont.testtask.backend.entities.Credit;
import com.haulmont.testtask.service.CreditService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class CreditEditUI extends VerticalLayout {
    private TextField creditNameField = new TextField("Наименование кредита");
    private TextField creditPercent = new TextField("Процент по кредиту");
    private TextField creditLimit = new TextField("Лимит по кредиту");
    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Credit credit;
    private CreditService creditService = new CreditService();
    private Binder<Credit> binder = new Binder<>();

    public CreditEditUI(Credit credit, CreditView creditView){
        setVisible(false);
        setWidthUndefined();
        this.credit = credit;
        HorizontalLayout layout = new HorizontalLayout();
        addClickListeners(creditView);
        layout.addComponents(add, update, delete, cancel);
        addComponents(creditNameField, creditPercent, creditLimit, layout);
    }

    public void editConfigure(Credit credit){
        binder.forField(creditNameField).withValidator(field -> field.length() > 0, "Поле не введено")
                .bind(Credit::getCreditName, Credit::setCreditName);
        binder.forField(creditPercent)
                .withValidator(field -> field.length() > 0, "Поле не введено")
                .withConverter(new StringToIntegerConverter("Поле введено неверно"))
                .withValidator(event -> event <= 100, "Введите правильное количество процентов")
                .bind(Credit::getPercent, Credit::setPercent);
        binder.forField(creditLimit)
                .withValidator(field -> field.length() > 0, "Поле не введено")
                .withConverter(new StringToLongConverter("Поле введено неверно"));
        setVisible(true);
        if (credit == null){
            clear();
            update.setVisible(false);
            delete.setVisible(false);
            add.setVisible(true);
        } else {
            this.credit = credit;
            setCredit(credit);
            update.setVisible(true);
            delete.setVisible(true);
            add.setVisible(false);
        }
        creditNameField.setPlaceholder("Введите название кредата");
        creditPercent.setPlaceholder("Процент кредита");
        creditLimit.setPlaceholder("Введите лимит по кредиту");
    }

    private void addClickListeners(CreditView creditView){
        cancel.addClickListener(event -> this.setVisible(false));
        add.addClickListener(event -> {
            try {
                if (fieldCheck()){
                    addCredit();
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                updateCredit();
                creditView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        delete.addClickListener(event -> {
            try {
                creditService.deleteCredit(credit);
                creditView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck(){
        if (creditNameField.isEmpty() || creditPercent.isEmpty() || creditLimit.isEmpty()){
            add.setComponentError(new UserError("Не все поля введены"));
            return false;
        }
        add.setComponentError(null);
        return true;
    }

    private void addCredit() throws SQLException{
        creditService.addCredit(getCredit());
    }

    private void updateCredit() throws SQLException{
        creditService.updateCredit(getCredit());
    }

    private void clear(){
        creditNameField.clear();
        creditPercent.clear();
        creditLimit.clear();
    }

    private Credit getCredit(){
        credit.setCreditName(creditNameField.getValue());
        credit.setPercent(Integer.parseInt(creditPercent.getValue()));
        credit.setCreditLimit(Long.parseLong(creditLimit.getValue()));
        return credit;
    }

    private void setCredit(Credit credit){
        creditNameField.setValue(credit.getCreditName());
        creditPercent.setValue(String.valueOf(credit.getPercent()));
        creditLimit.setValue(String.valueOf(credit.getCreditLimit()));
    }

}
