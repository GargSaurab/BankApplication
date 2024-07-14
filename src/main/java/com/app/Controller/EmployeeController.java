package com.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.ApiResponse;
import com.app.Dto.BankEmployeeDto;
import com.app.Dto.User;
import com.app.Service.BankEmployeeService;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    public BankEmployeeService beSrv;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody BankEmployeeDto empDto)
    {
        System.out.println(empDto);

        try {
            
             return ResponseEntity.status(HttpStatus.CREATED)
             .body(beSrv.addEmployee(empDto));

        } catch (Exception e) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse("Some error occured " + e.getMessage()));
        }
    }

   @PutMapping("/setPass")
    public ResponseEntity<?> setPassword(@RequestBody User user)
    {
        try {
            
             return ResponseEntity.status(HttpStatus.CREATED)
             .body(beSrv.setPassword(user));

        } catch (Exception e) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse("Some error occured " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeEmployee(@RequestParam Integer id)
    {
        try {
             return ResponseEntity.status(HttpStatus.OK)
             .body(beSrv.removeEmployee(id));
        }catch(ResourceNotFoundException re)
        {
            throw re;
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse("Some error occured " + e.getMessage()));
        }
    }



}
