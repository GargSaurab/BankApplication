package com.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.BankEmployeeRepo;
import com.app.Dto.BankEmployeeDto;
import com.app.Dto.User;
import com.app.Entity.BankEmployee;

import jakarta.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // Constructor Injection
public class BankEmployeeServiceImpl implements BankEmployeeService {

    public final BankEmployeeRepo beRep;

    public final ModelMapper mapper;

    public final PasswordEncoder passwordEncoder;

    // New Employee added
    @Override
    public void addEmployee(BankEmployeeDto empDto) {
      
        BankEmployee employee = mapper.map(empDto, BankEmployee.class);

        employee.setJobTitle(empDto.getJobTitle());

        employee.setPassword(passwordEncoder.encode(employee.getName()));

        System.out.println(employee);

        beRep.save(employee);

    }

     @Override
     public void setPassword(User user) {

        System.out.println(user);
        
         //first employee is fetched from database
         BankEmployee employee = beRep.findById(user.getId())
         .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
         
         // then pin is set to the customer
         employee.setPassword(passwordEncoder.encode(user.getPassword()));
 
         beRep.save(employee);
 
     }

     @Override
     public List<BankEmployeeDto> employeeList() {

         return beRep.findAll().stream() .map(emp -> mapper.map(emp, BankEmployeeDto.class))
                .collect(Collectors.toList());
 
     }

    // Employee removed
    @Override
    public void removeEmployee(int id) {
        
        beRep.deleteById(id);

    }

}
