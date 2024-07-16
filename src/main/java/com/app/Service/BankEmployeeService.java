package com.app.Service;

import org.springframework.stereotype.Service;

import com.app.Dto.BankEmployeeDto;
import com.app.Dto.User;

@Service
public interface BankEmployeeService {

   public void addEmployee(BankEmployeeDto empDto);

   public void setPassword(User user);

   public void removeEmployee(int id);

}
