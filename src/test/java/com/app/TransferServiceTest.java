package com.app;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.app.CustomException.InvalidInputException;
import com.app.Dao.CustomerRepo;
import com.app.Dao.TransactionRepo;
import com.app.Dto.User;
import com.app.Entity.Customer;
import com.app.Entity.Transaction;
import com.app.Service.TransactionServiceImpl;

public class TransferServiceTest {

    private TransactionServiceImpl transactionService;
    private CustomerRepo customerRepo;
    private TransactionRepo transactionRepo;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        customerRepo = mock(CustomerRepo.class);
        transactionRepo = mock(TransactionRepo.class);
        modelMapper = mock(ModelMapper.class);
        transactionService = new TransactionServiceImpl();
        // transactionService.custRep = customerRepo;
        // transactionService.trscRep = transactionRepo;
        //    transactionService.mapper = modelMapper;
    }

    @Test
    void testTransfer() {
        // Mock sender
        Customer sender = new Customer("Sender", 1000.0, "Sender Address");
        sender.setCustId(1);
        sender.setPin(1234);

        // Mock recipient
        Customer recipient = new Customer("Recipient", 500.0, "Recipient Address");
        recipient.setCustId(2);

        // Mock user input
        User user = new User();
        user.setId(1);
        user.setOtherId(2);
        user.setPin(1234);
        user.setAmount(200.0);

        // Mock repository behavior
        when(customerRepo.findById(1)).thenReturn(Optional.of(sender));
        when(customerRepo.findById(2)).thenReturn(Optional.of(recipient));

        // Perform transfer
        transactionService.transfer(user);

        // Verify balances after transfer
        assert sender.getBalance() == 800.0;
        assert recipient.getBalance() == 700.0;

        // Verify transaction logs
        verify(transactionRepo, times(2)).save(any(Transaction.class));
    }

    @Test
    void testTransferInsufficientBalance() {
        // Mock sender
        Customer sender = new Customer("Sender", 100.0, "Sender Address");
        sender.setCustId(1);
        sender.setPin(1234);

        // Mock recipient
        Customer recipient = new Customer("Recipient", 500.0, "Recipient Address");
        recipient.setCustId(2);

        // Mock user input
        User user = new User();
        user.setId(1);
        user.setOtherId(2);
        user.setPin(1234);
        user.setAmount(200.0);

        // Mock repository behavior
        when(customerRepo.findById(1)).thenReturn(Optional.of(sender));
        when(customerRepo.findById(2)).thenReturn(Optional.of(recipient));

        // Perform transfer and expect InvalidInputException
        try {
            transactionService.transfer(user);
        } catch (InvalidInputException e) {
            // Expected exception
        }

        // Verify balances unchanged
        assert sender.getBalance() == 100.0;
        assert recipient.getBalance() == 500.0;

        // Verify no transaction logs saved
        verify(transactionRepo, never()).save(any(Transaction.class));
    }
}
