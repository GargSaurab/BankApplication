package com.app.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.CommonResponse;
import com.app.Dto.CustomerDto;
import com.app.Dto.StatusCode;
import com.app.Dto.TransactionDto;
import com.app.Dto.User;
import com.app.Service.TransactionService;

@RestController
@RequestMapping("/transaction")
// @CrossOrigin()
public class TransactionController {

    @Autowired
    private TransactionService trscSrv;

    private Logger logger = LoggerFactory.getLogger(TransactionController.class);

    // using json to get the data securely from the user
    // Withdraw's money
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody User user) {
    
         CommonResponse response =  new CommonResponse();

        try {
            trscSrv.withdraw(user);

            response.info.code = StatusCode.Success;
            response.info.message = String.format(" %s withdrawed", user.getAmount());
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException re) {
        
            throw re; // if server have some issue
        
        } catch (InvalidInputException ie) {
        
            throw ie; // if user feeds wrong input
        
        }

    }

    // Deposits money
    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody User user) {

        CommonResponse response = new CommonResponse();

        try {
            trscSrv.deposit(user);

            response.info.code =  StatusCode.Success;
            response.info.message = String.format("%s deposited", user.getAmount());
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException re) {
       
            throw re;
       
        } catch (InvalidInputException ie) {
       
            throw ie;
       
        }

    }

    // returns list off all transactions done by a customer
    @PreAuthorize("hasRole('ROLE_EMP')")
    @GetMapping("/transactions")
    public ResponseEntity<?> listAllTransactions(@RequestParam int id) {

        CommonResponse response = new CommonResponse();

        try {
            List<TransactionDto> transactions = trscSrv.listAllTransactions(id);

            response.info.code = StatusCode.Success;
            response.info.message = "Success";
            response.data = transactions;
            return ResponseEntity.ok(response);
            
        } catch (ResourceNotFoundException re) {
            throw re;
        }

    }

    // performs money transfer between accounts
    @PutMapping("transfer")
    public ResponseEntity<?> transfer(@RequestBody User user) {
        System.out.println(user);
        CommonResponse response = new CommonResponse();

        try {
             trscSrv.transfer(user);

            response.info.code = StatusCode.Success;
            response.info.message = String.format("%s transfered", user.getAmount());
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException re) {
      
            throw re;
      
        } catch (InvalidInputException ie) {
      
            throw ie;
      
        }
    }

     
     @PreAuthorize("hasRole('EMP')")
     @PostMapping("/getCustomerbyTrsc")
     public ResponseEntity<?> getCustomerByTransaction(@RequestParam int id) {
 
         CommonResponse response = new CommonResponse();

         logger.info("In the getCustomerByTrsc in controller");
 
         try {
             CustomerDto customer = trscSrv.getCustomerByTransaction(id);
 
             response.info.code = StatusCode.Success;
             response.info.message = "Success";
             response.data = customer;
             return ResponseEntity.ok(response);
             
         } catch (ResourceNotFoundException re) {
             throw re;
         }
 
     }

}
