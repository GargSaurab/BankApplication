package com.app.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.CustomerRepo;
import com.app.Dto.ApiResponse;
import com.app.Dto.CustomerDto;
import com.app.Dto.User;
import com.app.Entity.Customer;

public class CustomerServiceImpl implements CustomerService{
    
    @Autowired
    private CustomerRepo custRep;
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public ApiResponse addCustomer(CustomerDto customerDto) {
        
        custRep.save(mapper.map(customerDto, Customer.class));

        return new ApiResponse("New Customer added");
    }

    @Override
    public ApiResponse setPin(User user) {
       
        Customer customer = custRep.findById(user.getId())
        .orElseThrow(new ResourceNotFoundException("Customer not found"));

    }
}
