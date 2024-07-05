package com.app.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {
    private int custId;
    private String name;
    private double balance;
    private String address;
}
