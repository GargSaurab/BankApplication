package com.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.ApiResponse;
import com.app.Dto.CommonResponse;
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

    // using json to get the data securely from the user
    // Withdraw's money
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody User user) {

        try {
            trscSrv.withdraw(user);
            ApiResponse response = new ApiResponse(user.getAmount() + "withdrawed");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException re) {
            throw re; // if server have some issue
        } catch (InvalidInputException ie) {
            throw ie; // if user feeds wrong input
        }

    }

    // Deposits money
    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody User user) {

        try {
            trscSrv.deposit(user);
            ApiResponse response = new ApiResponse(user.getAmount() + "deposited");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException re) {
            throw re;
        } catch (InvalidInputException ie) {
            throw ie;
        }

    }

    // returns list off all transactions done by a customer
    @PreAuthorize("hasRole('EMP')")
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
        try {
            trscSrv.deposit(user);
            ApiResponse response = new ApiResponse(user.getAmount() + "transfered");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException re) {
            throw re;
        } catch (InvalidInputException ie) {
            throw ie;
        }
    }

}
