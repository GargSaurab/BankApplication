package com.app.Entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BankEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int empId;
    private String name;
    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;
    private String password;

}
