package com.app.Service;

import org.springframework.stereotype.Service;

import com.app.Dto.ApiResponse;
import com.app.Dto.BankEmployeeDto;

@Service
public interface BankEmployeeService {

   public ApiResponse addEmployee(BankEmployeeDto empDto);

   public ApiResponse removeEmployee(int id);

}
