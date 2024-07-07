package com.app.Service;

import com.app.Dto.ApiResponse;
import com.app.Dto.CustomerDto;
import com.app.Dto.User;

public interface CustomerService {
     
     public ApiResponse addCustomer(CustomerDto customer);

     public ApiResponse setPin(User user);
}
