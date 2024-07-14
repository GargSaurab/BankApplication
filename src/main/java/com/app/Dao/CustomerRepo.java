package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Integer>{

    public Customer findByName(String name);
}
