package com.app.Dto;

import java.time.LocalDateTime;

import com.app.Entity.Customer;
import com.app.Entity.TrscType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDto {

    private int trscId;
    private double amount;
    private LocalDateTime time;
    private TrscType type;
    public Customer customer;
    public Customer from_To;
}
