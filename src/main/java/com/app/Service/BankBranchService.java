package com.app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.Dto.BankBranchDto;

@Service
public interface BankBranchService {

    public BankBranchDto getBranch(int id);

    public List<BankBranchDto> getBranchList();

}
