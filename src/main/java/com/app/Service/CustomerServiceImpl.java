package com.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.CustomerRepo;
import com.app.Dto.ApiResponse;
import com.app.Dto.CustomerDto;
import com.app.Dto.User;
import com.app.Entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService{
    
    @Autowired
    private CustomerRepo custRep;
    
    @Autowired
    private ModelMapper mapper;

    //adding customer
    @Override
    public ApiResponse addCustomer(CustomerDto customerDto) {
        
        custRep.save(mapper.map(customerDto, Customer.class));

        return new ApiResponse("New Customer added");
    }

    // Setting pin
    @Override
    public ApiResponse setPin(User user) {
       
        //first customer is fetched from database
        Customer customer = custRep.findById(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        
        // then pin is set to the customer
        customer.setPin(user.getPin());

        custRep.save(customer);

        // returns an apiresponse for confirmation
        return new ApiResponse("New Pin is Set");

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
