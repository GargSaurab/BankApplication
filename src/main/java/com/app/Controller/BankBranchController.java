package com.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dto.BankBranchDto;
import com.app.Dto.CommonResponse;
import com.app.Dto.StatusCode;
import com.app.Service.BankBranchService;

@RestController
@RequestMapping("/bankBranch")
public class BankBranchController {

    @Autowired
    private BankBranchService brchSrv;
 
    @PostMapping("/branch")
    public ResponseEntity<?> getBranch(@RequestParam int id)
    {
        CommonResponse response = new CommonResponse();

        try {
             
         System.out.println("In the branchController");

         BankBranchDto branch = brchSrv.getBranch(id);
         
         response.info.code = StatusCode.Success;
         response.info.message = String.format("%s data fetched", branch.getBranchName());
         response.data = branch;

         return ResponseEntity.ok(response);

        } catch(ResourceNotFoundException re)
        {
            throw re;
        }       
        catch (Exception e) {
           
            throw e;
        }
    }

    @GetMapping("/branchlist")
    public ResponseEntity<?> getBranchList()
    {
        CommonResponse response = new CommonResponse();

        try {
             
         System.out.println("In the branchController");

         List<BankBranchDto> branch = brchSrv.getBranchList();
         
         response.info.code = StatusCode.Success;
         response.info.message = "fetched list of all branches";
         response.data = branch;

         return ResponseEntity.ok(response);

        } catch(ResourceNotFoundException re)
        {
            throw re;
        }       
        catch (Exception e) {
           
            throw e;
        }
    }
    
}
