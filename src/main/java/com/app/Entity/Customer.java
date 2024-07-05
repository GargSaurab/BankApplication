package com.app.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {
    
    @Id
    private int custId;
    private String name;
    private double balance;
    private int pin;
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

}
