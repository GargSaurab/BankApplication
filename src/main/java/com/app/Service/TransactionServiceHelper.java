package com.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.CustomerRepo;
import com.app.Dao.TransactionRepo;
import com.app.Entity.Customer;
import com.app.Entity.Transaction;
import com.app.Entity.TrscType;

import jakarta.transaction.Transactional;

@Transactional
@Component
public class TransactionServiceHelper {

    @Autowired
    private CustomerRepo custRep;

    @Autowired
    private TransactionRepo trscRep;


    public Customer getCustomer(int id)
    {
        return custRep.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not  found")); // fetching customer fr
        // database to match the pin
        // and add the amount
    }


    public void deposit(Customer  customer,  double amount) {

        double balance = customer.getBalance();

        customer.setBalance(balance + amount);

        custRep.save(customer);// saving the updated customer data

        // Loging the transaction
        Transaction newTransaction = new Transaction(amount, TrscType.TRANSFER_CREDIT, customer, null);

        trscRep.save(newTransaction);
    }

    public void withdraw(Customer  customer, double amount) {
        
        double balance = customer.getBalance();

        customer.setBalance(balance - amount);

        custRep.save(customer);// saving the updated customer data

        // Loging the transaction
        Transaction newTransaction = new Transaction(amount, TrscType.TRANSFER_DEBIT, customer, null);

        trscRep.save(newTransaction);
    }

}
