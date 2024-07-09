package com.app.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String password;
    private double balance;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int pin;
    private String address;
    public String role;

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



