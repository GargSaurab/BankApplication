package com.app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.Dto.CustomerDto;
import com.app.Dto.TransactionDto;
import com.app.Dto.User;

@Service
public interface TransactionService {

  public void withdraw(User user);

  public void deposit(User user);

  public List<TransactionDto> listAllTransactions(int id);

  public void transfer(User user);

   public CustomerDto getCustomerByTransaction(int id);

   public TransactionDto getTransaction(int id);

}
