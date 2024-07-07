package com.app.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int custId;
    private String name;
    private double balance;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int pin;
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    public Customer(String name, double balance, String address) {
        this.name = name;
        this.balance = balance;
        this.address = address;
    }

    // getter & setter so that pin can be handled manualy
    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    // helper method to add transaction, for association
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setCustomer(this);
    }


}



