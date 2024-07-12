package com.app.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    
    private int id;
    private int pin;  
    private int otherId;
    private double  amount;
    private String password;

}
