package com.app.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int empId;
    private String name;
    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;
    private String password;
    public String role;

}
