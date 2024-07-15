package com.app.Service;

import java.util.List;

import com.app.Dto.CustomerDto;
import com.app.Dto.User;

public interface CustomerService {

     public void addCustomer(CustomerDto customer);

     public void setPin(User user);

     public void setPassword(User user);

     public List<CustomerDto> listAllCustomers();

     public CustomerDto getCustomerById(User user);
}
