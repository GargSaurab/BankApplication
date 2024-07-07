package com.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.CustomerRepo;
import com.app.Dao.TransactionRepo;
import com.app.Dto.TransactionDto;
import com.app.Dto.User;
import com.app.Entity.Customer;
import com.app.Entity.Transaction;
import com.app.Entity.TrscType;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    public TransactionRepo trscRep;

    @Autowired
    public CustomerRepo custRep;

    @Autowired
    public ModelMapper mapper;

    @Override
    public void withdraw(User user) {

        Customer customer = custRep.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not  found"));// fetching customer  from
                                                                                         // database to match the pin
                                                                                         // and deduct the amount

        // checking user give pin
        if (customer.getPin() != (user.getPin())) {
            throw new InvalidInputException("Invalid Pin");
        }

        double balance = customer.getBalance(); 
        double amount = user.getAmount();

         // if amount is greater then balance throws insufficient balance
        if (balance < amount) {
            throw new InvalidInputException("Insufficient Balance");
        }

        customer.setBalance(balance - amount); // updating balance

        custRep.save(customer); // saving the updated customer data


        
        // Loging the transaction
        Transaction newTransaction = new Transaction(user.getAmount(),TrscType.WITHDRAWAL,customer,null);

        trscRep.save(newTransaction);

    }

    @Override
    public void deposit(User user) {

        Customer customer = custRep.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not  found")); // fetching customer from database to match the pin and add the amount

        // checks pin given by user
        if (customer.getPin() != (user.getPin())) {
            throw new InvalidInputException("Invalid Pin");
        }

        double balance = customer.getBalance();
        double amount = user.getAmount();

        customer.setBalance(balance + amount);

        custRep.save(customer);// saving the updated customer data


        // Loging the transaction
        Transaction newTransaction = new Transaction(user.getAmount(),TrscType.DEPOSIT,customer,null);

        trscRep.save(newTransaction);

    }

    @Override
    public List<TransactionDto> listAllTransactions(int id) {

        Customer customer = custRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not  found")); // fetching customer from
                                                                                          // database

        return trscRep.findAllByCustomer(customer)
                .stream()
                .map(trsc -> mapper.map(trsc, TransactionDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public void transfer(User user) {

        // fetching data of the sender
        Customer sender = custRep.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("sender not  found")); 

        // fetching data of the recepient        
        Customer recepient = custRep.findById(user.getOtherId())
        .orElseThrow(() -> new ResourceNotFoundException("recepient not  found")); 


        // checks pin given by user
        if (sender.getPin() != (user.getPin())) {
            throw new InvalidInputException("Invalid Pin");
        }

        double sBalance = sender.getBalance();
        double rBalance = recepient.getBalance();
        double amount = user.getAmount();

        // checking if balanace is sufficient or not
        if (sBalance < amount) {
            throw new InvalidInputException("Insufficient Balance");
        }

        // updating balance and saving the updated dat of both sender and recepient
        recepient.setBalance(sBalance - amount);

        custRep.save(recepient);

        recepient.setBalance(rBalance + amount);

        custRep.save(recepient);

        // logging the new transaction
        Transaction transaction1 = new Transaction(user.getAmount(),TrscType.TRANSFER_CREDIT,sender,recepient);

        trscRep.save(transaction1);
      
        Transaction transaction2 = new Transaction(user.getAmount(),TrscType.TRANSFER_DEBIT,recepient,sender);

        trscRep.save(transaction2);

        
        
    }

}
