package com.app.Entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int trscId;
    private double amount;
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private TrscType type;

    @ManyToOne
    @JoinColumn(name = "Customer_id")
    public Customer customer;

    public Transaction(double amount, TrscType type, Customer customer)
    {
        this.amount = amount;
        this.time = LocalDateTime.now();
        this.type = type;
        this.customer = customer;
    }

}
