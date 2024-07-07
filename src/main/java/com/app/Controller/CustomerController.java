package com.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Dto.ApiResponse;
import com.app.Dto.CustomerDto;
import com.app.Dto.User;
import com.app.Service.CustomerService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/Cusomer")
public class CustomerController {

   @Autowired
   private CustomerService custSrv;

   // Adding Customer to database
   @GetMapping("/add")
   public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customer)
   {
      try
        { 
           return ResponseEntity.status(HttpStatus.CREATED)
           .body(custSrv.addCustomer(customer));
        }
      catch(Exception e)
      {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
         .body(new ApiResponse("Some error occured"));
      }
   }

   // seting pin given by customer
   @PutMapping("/setPin")
   public ResponseEntity<?> setPin(@RequestBody User user)
   {
      try
      { 
         return ResponseEntity.status(HttpStatus.OK)
         .body(custSrv.setPin(user));
      }
    catch(Exception e)
    {
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
       .body(new ApiResponse("Some error occured"));
    }
   }

   public  ResponseEntity<?> listAllCustomers()
   {
      try {
          
      } catch (Exception e) {
         // TODO: handle exception
      }
   }
 


}
