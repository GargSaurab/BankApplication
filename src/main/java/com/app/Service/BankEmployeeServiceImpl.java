package com.app.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.Dao.BankEmployeeRepo;
import com.app.Dto.ApiResponse;
import com.app.Dto.BankEmployeeDto;
import com.app.Entity.BankEmployee;

public class BankEmployeeServiceImpl implements BankEmployeeService {

    @Autowired
    public BankEmployeeRepo beRep;

    @Autowired
    public ModelMapper mapper;

    // New Employee added
    @Override
    public ApiResponse addEmployee(BankEmployeeDto empDto) {
      
        beRep.save(mapper.map(empDto, BankEmployee.class));

        return new ApiResponse("New Employee joined");
    }

    // Employee removed
    @Override
    public ApiResponse removeEmployee(int id) {
        
        beRep.deleteById(id);

        return new ApiResponse(id + " employee is removed");
    }

    

}
