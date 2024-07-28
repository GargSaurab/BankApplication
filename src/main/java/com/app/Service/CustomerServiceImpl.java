package com.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.CustomerRepo;
import com.app.Dto.CustomerDto;
import com.app.Dto.User;
import com.app.Entity.Customer;

import jakarta.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // Constructor Injection
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepo custRep;

    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    //adding customer
    @Override
    public void addCustomer(CustomerDto customerDto) {

       Customer customer =  mapper.map(customerDto, Customer.class);

       customer.setPassword(passwordEncoder.encode(customer.getName()));
        
        custRep.save(customer);

    }

    // Setting pin
    @Override
    public void setPin(User user) {

       
       
        //first customer is fetched from database
        Customer customer = custRep.findById(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        
        // then pin is set to the customer
        customer.setPin(user.getPin());

        custRep.save(customer);

    }

     // Setting password
     @Override
     public void setPassword(User user) {
        
         //first customer is fetched from database
         Customer customer = custRep.findById(user.getId())
         .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
         
         // then pin is set to the customer
         customer.setPassword(passwordEncoder.encode(user.getPassword()));
 
         custRep.save(customer);

 
     }

    // fetches whole list of customers
    @Override
    public List<CustomerDto> listAllCustomers() {
        
        return custRep.findAll().stream()
        .map(cust -> mapper.map(cust, CustomerDto.class))
        .collect(Collectors.toList());
    }

    // Customer can fetch data about themselves
    @Override
    public CustomerDto getCustomerById(User user) {

       Customer customer = custRep.findById(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if(customer.getPin() != user.getPin())
        {
            throw new InvalidInputException("Invalid Pin");
        }

        return mapper.map(customer, CustomerDto.class);
    }

    
}
