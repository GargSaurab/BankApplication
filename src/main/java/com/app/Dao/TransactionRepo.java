package com.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entity.Customer;
import com.app.Entity.Transaction;


public interface TransactionRepo extends JpaRepository<Transaction,Integer>{


     List<Transaction> findAllByCustomer(Customer customer);

}
