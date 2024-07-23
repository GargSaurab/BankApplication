package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.Entity.BankEmployee;

@Repository
public interface BankEmployeeRepo extends JpaRepository<BankEmployee,Integer>{

     public BankEmployee findByName(String name);

}
