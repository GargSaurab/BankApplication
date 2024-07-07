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

    public User(int id, int pin)// constructor created for reseting the pin, for which we only need id and pin
    {
        this.id = id;
        this.pin = pin;
    }

}
