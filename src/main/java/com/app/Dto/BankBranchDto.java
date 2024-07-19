package com.app.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankBranchDto {

    private int branchId;

    private String branchName;

    private String address;

    private int empNo;
    
    private String phoneNumber;

    private String email;

}
