package com.app.Entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trscId;
    private double amount;
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private TrscType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer_id")
    public Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_to_id")
    public Customer from_To;

    public Transaction(double amount, TrscType type, Customer customer,Customer from_To)
    {
        this.amount = amount;
        this.time = LocalDateTime.now();
        this.type = type;
        this.customer = customer;
        this.from_To = from_To;
    }

}
