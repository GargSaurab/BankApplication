package com.app.Dto;

import com.app.Entity.JobTitle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankEmployeeDto {

    private int empId;
    private String name;
    private JobTitle jobTitle;
    private String role;
    
}
