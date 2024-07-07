package com.app.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankEmployeeDto {

    private int empId;
    private String name;
    private JobTitleDto jobTitle;

}
