package com.app.Controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.BankEmployeeDto;
import com.app.Dto.CommonResponse;
import com.app.Dto.StatusCode;
import com.app.Dto.User;
import com.app.Service.BankEmployeeService;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor // Costructor Injection
public class EmployeeController {

    public final BankEmployeeService beSrv;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody BankEmployeeDto empDto) {
        System.out.println(empDto);

        CommonResponse response = new CommonResponse();

        try {

            beSrv.addEmployee(empDto);

            response.info.code = StatusCode.Success;
            response.info.message = "New employee added.";
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            throw e;

        }
    }

    @PutMapping("/setPass")
    public ResponseEntity<?> setPassword(@RequestBody User user) {
        CommonResponse response = new CommonResponse();

        try {

            beSrv.setPassword(user);

            response.info.code = StatusCode.Success;
            response.info.message = "New password is set.";
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            throw e;

        }
    }

    @GetMapping("/getEmpList")
    public ResponseEntity<?> employeeList() {
        CommonResponse response = new CommonResponse();

        try {

            List<BankEmployeeDto> employees = beSrv.employeeList();

            response.info.code = StatusCode.Success;
            response.info.message = "All employee's details fetched";
            response.data = employees;
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            throw e;

        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeEmployee(@RequestParam Integer id) {
        CommonResponse response = new CommonResponse();

        try {

            beSrv.removeEmployee(id);

            response.info.code = StatusCode.Success;
            response.info.message = "Employee removed.";
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException re) {
            throw re;
        } catch (Exception e) {

            throw e;

        }
    }

}
