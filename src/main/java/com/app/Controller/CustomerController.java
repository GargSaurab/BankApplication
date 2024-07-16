package com.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.app.Dto.CommonResponse;
import com.app.Dto.CustomerDto;
import com.app.Dto.StatusCode;
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

      CommonResponse response = new CommonResponse();

      try {

         custSrv.addCustomer(customer);

         response.info.code = StatusCode.Success;
         response.info.message = "Succesfully added new customer.";
         return ResponseEntity.ok(response);

      } catch (Exception e) {
     
         throw e;
     
      }
   }

   // setting pin given by customer
   @Secured("CUST")
   @PutMapping("/setPin")
   public ResponseEntity<?> setPin(@RequestBody User user) {

      CommonResponse response = new CommonResponse();

      try {

         custSrv.setPin(user);

         response.info.code = StatusCode.Success;
         response.info.message = "New pin set.";
         return ResponseEntity.ok(response);

      } catch (ResourceNotFoundException re) {
     
         throw re;
     
      } catch (Exception e) {
     
         throw e;
     
      }
   }

   @Secured("CUST")
   @PutMapping("/setPass")
   public ResponseEntity<?> setPassword(@RequestBody User user) {

       CommonResponse response = new CommonResponse();

      try {
         custSrv.setPassword(user);

         response.info.code = StatusCode.Success;
         response.info.message = "New password set.";
         return ResponseEntity.ok(response);
      } catch (ResourceNotFoundException re) {

         throw re;
      
      } catch (Exception e) {
      
         throw e;
      
      }
   }

   // Employee can fetch list of all the customers
   @Secured("EMP")
   @GetMapping("/listcustomers")
   public ResponseEntity<?> listAllCustomers() {

      CommonResponse response = new CommonResponse();

      try {
         
         response.info.code = StatusCode.Success;
         response.info.message = "List of customers fetched.";
         response.data = custSrv.listAllCustomers();
         return ResponseEntity.ok(response);

      } catch (Exception e) {
        
         throw e;

      }
   }

   // Customer can get info about themselve
   @Secured("CUST")
   @PostMapping("/customerbyid")
   public ResponseEntity<?> getCustomerById(@RequestBody User user) {
       
         CommonResponse response = new CommonResponse();

      try {

         CustomerDto customer = custSrv.getCustomerById(user);
         
         response.info.code =  StatusCode.Success;
         response.info.message = String.format("%s data is fetched", customer.getName());
         response.data = customer;
         return ResponseEntity.ok(response);
      } catch (InvalidInputException re) {
      
         throw re;
      
      } catch (Exception e) {
        
         throw e;
      
      }
   }

}
