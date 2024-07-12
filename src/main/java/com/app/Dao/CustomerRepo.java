package com.app.Dao;

import com.app.Entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Integer>{

    public Customer findByName(String name);
}
