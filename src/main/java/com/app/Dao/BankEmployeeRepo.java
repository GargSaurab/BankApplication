package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entity.BankEmployee;

public interface BankEmployeeRepo extends JpaRepository<BankEmployee,Integer>{

     public BankEmployee findByName(String name);

}
