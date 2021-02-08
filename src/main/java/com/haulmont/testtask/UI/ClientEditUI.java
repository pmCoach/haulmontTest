package com.haulmont.testtask.UI;

import com.haulmont.testtask.backend.entities.Client;
import com.haulmont.testtask.service.ClientService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class ClientEditUI extends VerticalLayout{
    private ClientService clientService = new ClientService();
    private TextField nameField = new TextField("Имя");
    private TextField secnameField = new TextField("Фамилия");
    private TextField patronymicField = new TextField("Отчество");
    private TextField phone_numberField =  new TextField("Номер телефона");
    private TextField email = new TextField("Электронная почта");
    private TextField pasport_numberField = new TextField("Номер паспорта");
    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Client client;
    private Binder<Client> binder = new Binder<>();

    public ClientEditUI(Client client, ClientView clientView){
        this.client = client;
        setVisible(false);
        setWidthUndefined();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> this.setVisible(false));
        addClickListeners(clientView);
        layout.addComponents(add, delete, update, cancel);
        addComponents(nameField, secnameField, patronymicField, phone_numberField, email, pasport_numberField, layout);
    }

    public void editConfigure(Client client){
        setVisible(true);
        if (client == null){
            clear();
            add.setVisible(true);
            delete.setVisible(false);
            update.setVisible(false);
        } else {
            this.client = client;
            setClient(client);
            add.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
        }
        nameField.setPlaceholder("Введите имя");
        secnameField.setPlaceholder("Введите фамилию");
        patronymicField.setPlaceholder("Введите отчество");
        phone_numberField.setPlaceholder("Введите номер телефона");
        email.setPlaceholder("Введите почту");
        pasport_numberField.setPlaceholder("Введите номер паспорта");
    }

    private void clear(){
        nameField.clear();
        secnameField.clear();
        patronymicField.clear();
        phone_numberField.clear();
        email.clear();
        pasport_numberField.clear();
    }

    private void addClickListeners(ClientView clientView){
        add.addClickListener(event -> {
            try {
                if (fieldCheck()){
                    addClient();
                    clientView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                updateClient(client);
                clientView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        delete.addClickListener(event -> {
            try {
                clientService.deleteClient(client);
                clientView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck(){
        if (nameField.isEmpty() || secnameField.isEmpty() || patronymicField.isEmpty() || email.isEmpty() || phone_numberField.isEmpty() || pasport_numberField.isEmpty()){
            add.setComponentError(new UserError("Не все поля введены"));
            return false;
        } else {
            add.setComponentError(null);
            return true;
        }
    }

    private void setClient(Client client){
        nameField.setValue(client.getName());
        secnameField.setValue(client.getSecname());
        patronymicField.setValue(client.getPatronymic());
        phone_numberField.setValue(client.getPhoneNumber());
        email.setValue(client.getEmail());
        pasport_numberField.setValue(client.getPasportNumber());
    }

    private void addClient() throws SQLException {
        Client client = new Client(nameField.getValue(),
                secnameField.getValue(),
                patronymicField.getValue(),
                phone_numberField.getValue(),
                email.getValue(),
                pasport_numberField.getValue());
        clientService.addClient(client);
    }

    private void updateClient(Client client) throws SQLException{
        client.setName(nameField.getValue());
        client.setSecname(secnameField.getValue());
        client.setPatronymic(patronymicField.getValue());
        client.setPhoneNumber(phone_numberField.getValue());
        client.setEmail(email.getValue());
        client.setPasportNumber(pasport_numberField.getValue());
        clientService.updateClient(client);
    }
}
