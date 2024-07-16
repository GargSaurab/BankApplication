package com.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.CustomException.InvalidInputException;
import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.ApiResponse;
import com.app.Dto.CustomerDto;
import com.app.Dto.User;
import com.app.Service.CustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerController {

   @Autowired
   private CustomerService custSrv;

   // Adding Customer to database
   @Secured("EMP")
   @PostMapping("/add")
   public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customer) {
      try {
         return ResponseEntity.status(HttpStatus.CREATED)
               .body(custSrv.addCustomer(customer));
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ApiResponse("Some error occured " + e.getMessage()));
      }
   }

   // setting pin given by customer
   @Secured("CUST")
   @PutMapping("/setPin")
   public ResponseEntity<?> setPin(@RequestBody User user) {
      try {
         return ResponseEntity.status(HttpStatus.OK)
               .body(custSrv.setPin(user));
      } catch (ResourceNotFoundException re) {
         throw re;
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ApiResponse("Some error occured " + e.getMessage()));
      }
   }

   @Secured("CUST")
   @PutMapping("/setPass")
   public ResponseEntity<?> setPassword(@RequestBody User user) {
      try {
         return ResponseEntity.status(HttpStatus.OK)
               .body(custSrv.setPassword(user));
      } catch (ResourceNotFoundException re) {
         throw re;
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ApiResponse("Some error occured " + e.getMessage()));
      }
   }

   // Employee can fetch list of all the customers
   @Secured("EMP")
   @GetMapping("/listcustomers")
   public ResponseEntity<?> listAllCustomers() {
      try {
         return ResponseEntity.ok()
               .body(custSrv.listAllCustomers());
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ApiResponse("Some error occured " + e.getMessage()));
      }
   }

   // Customer can get info about themselve
   @Secured("CUST")
   @PostMapping("/customerbyid")
   public ResponseEntity<?> getCustomerById(@RequestBody User user) {
       
      try {
         return ResponseEntity.ok()
         .body(custSrv.getCustomerById(user));
      } catch (InvalidInputException re) {
         throw re;
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ApiResponse("Some error occured " + e.getMessage()));
      }
   }
   

}
