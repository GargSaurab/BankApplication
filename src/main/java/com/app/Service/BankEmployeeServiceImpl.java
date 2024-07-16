package com.app.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.BankEmployeeRepo;
import com.app.Dto.ApiResponse;
import com.app.Dto.BankEmployeeDto;
import com.app.Dto.User;
import com.app.Entity.BankEmployee;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BankEmployeeServiceImpl implements BankEmployeeService {

    @Autowired
    public BankEmployeeRepo beRep;

    @Autowired
    public ModelMapper mapper;
    
    @Autowired
    public PasswordEncoder passwordEncoder;
    ;

    // New Employee added
    @Override
    public ApiResponse addEmployee(BankEmployeeDto empDto) {
      
        BankEmployee employee = mapper.map(empDto, BankEmployee.class);

        employee.setPassword(passwordEncoder.encode(employee.getName()));

        beRep.save(employee);

        return new ApiResponse("New Employee joined");
    }

     @Override
     public ApiResponse setPassword(User user) {

        System.out.println(user);
        
         //first employee is fetched from database
         BankEmployee employee = beRep.findById(user.getId())
         .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
         
         // then pin is set to the customer
         employee.setPassword(passwordEncoder.encode(user.getPassword()));
 
         beRep.save(employee);
 
         // returns an apiresponse for confirmation
         return new ApiResponse("New Password is Set");
 
     }

    // Employee removed
    @Override
    public ApiResponse removeEmployee(int id) {
        
        beRep.deleteById(id);

        return new ApiResponse(id + " employee is removed");
    }

    

}
