package com.haulmont.testtask.backend.entities;


import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;


@Entity
public class CreditOffer {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditId", nullable = false)
    private Credit credit;

    private Long creditSum;
    private Long monthsOfCredit;
    @OneToMany(mappedBy = "creditOffer", fetch = FetchType.EAGER)
    private Set<PaymentGraphic> payment_graphics;

    public Long getMonthsOfCredit() {
        return monthsOfCredit;
    }

    public void setMonthsOfCredit(Long monthsOfCredit) {
        this.monthsOfCredit = monthsOfCredit;
    }

    public Set<PaymentGraphic> getPayment_graphics() {
        return payment_graphics;
    }

    public void setPayment_graphics(Set<PaymentGraphic> payment_graphics) {
        this.payment_graphics = payment_graphics;
    }

    public CreditOffer(){

    }

    public CreditOffer(Client client, Credit credit, Long creditSum, Long monthsOfCredit) {
        this.client = client;
        this.credit = credit;
        this.creditSum = creditSum;
        this.monthsOfCredit = monthsOfCredit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(Long creditSum) {
        this.creditSum = creditSum;
    }

    public Set getPayment_graphic() {
        return payment_graphics;
    }

    public void setPayment_graphic(PaymentGraphic payment_graphic) {
        payment_graphics.add(payment_graphic);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
