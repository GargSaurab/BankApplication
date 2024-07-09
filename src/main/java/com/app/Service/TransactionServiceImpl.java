package com.app.Service;

import java.util.List;
import java.util.stream.Collectors;

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

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    public TransactionRepo trscRep;

    @Autowired
    public CustomerRepo custRep;

    @Autowired
    public ModelMapper mapper;

    // Withdraw's the money out
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

    // Deposits the money in 
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

    // lists all the transactions made by each id
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

    // transfers money from one customer to another
    @Override
    public void transfer(User user) {

        // fetching data of the sender
        Customer sender = custRep.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("sender not found")); 

        // fetching data of the recepient        
        Customer recipient = custRep.findById(user.getOtherId())
        .orElseThrow(() -> new ResourceNotFoundException("recepient not found")); 

        // checks pin given by user
        if (sender.getPin() != (user.getPin())) {
            throw new InvalidInputException("Invalid Pin");
        }

        double senderBalance = sender.getBalance();
        double recipientBalance = recipient.getBalance();
        double amount = user.getAmount();

        // checking if balanace is sufficient or not
        if (senderBalance < amount) {
            throw new InvalidInputException("Insufficient Balance");
        }

        // updating balance and saving the updated dat of both sender and recipient
        sender.setBalance(senderBalance - amount);
        recipient.setBalance(recipientBalance + amount);
        
        System.out.println(sender);
        
        custRep.save(sender);
        custRep.save(recipient);
        
        // logging the new transaction
        Transaction transaction1 = new Transaction(user.getAmount(),TrscType.TRANSFER_CREDIT,sender,recipient);

        trscRep.save(transaction1);
      
        Transaction transaction2 = new Transaction(user.getAmount(),TrscType.TRANSFER_DEBIT,recipient,sender);

        trscRep.save(transaction2);

    }

}
