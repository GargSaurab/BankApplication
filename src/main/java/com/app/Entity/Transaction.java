package com.app.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "Customer_id")
    public Customer customer;

    @ManyToOne
    @JoinColumn(name = "from_to_id")
    public Customer from_To;

    public Transaction(double amount, TrscType type, Customer customer,Customer from_To)
    {
        this.amount = amount;
        this.time = LocalDateTime.now();
        this.type = type;
        this.customer = customer;
        this.from_To = from_To;
        this.customer.addTransaction(this);
    }

}
