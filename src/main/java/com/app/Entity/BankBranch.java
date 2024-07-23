package com.app.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BankBranch {
    
    @Id
    private int branchId;

    private String branchName;

    private String address;

    private int empNo;
    
    private String phoneNumber;

    private String email;

}
