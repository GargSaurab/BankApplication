package com.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private TransactionRepo trscRep;

    @Autowired
    private CustomerRepo custRep;

    @Autowired
    private TransactionServiceHelper helper;

    @Autowired
    private ModelMapper mapper;

    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

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
        Customer sender = helper.getCustomer(user.getId());

        logger.info(sender.toString());
        
        // checks pin given by user
        if (sender.getPin() != (user.getPin())) {
            throw new InvalidInputException("Invalid Pin");
        }
        
        helper.withdraw(sender, user.getAmount());
        
        Customer recipient = helper.getCustomer(user.getOtherId());
        
        logger.info(recipient.toString());

        helper.deposit(recipient, user.getAmount());
        

    }

}
