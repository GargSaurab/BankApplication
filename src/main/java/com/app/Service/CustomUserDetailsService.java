package com.app.Service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.BankEmployeeRepo;
import com.app.Dao.CustomerRepo;
import com.app.Entity.BankEmployee;
import com.app.Entity.BankEmployeeDetails;
import com.app.Entity.Customer;
import com.app.Entity.CustomerDetails;

@Service
@RequiredArgsConstructor // Cosntructor Injcetion
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepo custRepo;

    private final BankEmployeeRepo empRepo;

    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info(username);

        Customer cust = custRepo.findByName(username);
        if(cust != null)
        {
            System.out.println(cust.toString());

            return new CustomerDetails(cust);
        }

        BankEmployee emp = empRepo.findByName(username);
        if(emp != null)
        {
            System.out.println(emp.toString());

            return new BankEmployeeDetails(emp);
        }
        throw new ResourceNotFoundException("User not Found");

    }
}
