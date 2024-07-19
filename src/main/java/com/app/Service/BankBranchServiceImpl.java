package com.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.BankBranchRepo;
import com.app.Dao.ProcedureRepo;
import com.app.Dto.BankBranchDto;
import com.app.Entity.BankBranch;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BankBranchServiceImpl implements BankBranchService {

    @Autowired
    private BankBranchRepo brnchRep;
    
    @Autowired
    private ProcedureRepo prRep;

    @Autowired
    private ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(BankBranchServiceImpl.class);

    @Override
    public BankBranchDto getBranch(int id) {

        logger.info("In the branch service");

        BankBranch branch = brnchRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Some error occured!"));

        logger.info(branch.toString());

        return mapper.map(branch, BankBranchDto.class);
    }

    @Override
    public List<BankBranchDto> getBranchList() {

        logger.info("In the branch service");

        List<BankBranch> branches = prRep.getBranchList();

        if (!branches.isEmpty()) {
            return branches.stream().map(branch -> mapper.map(branch, BankBranchDto.class))
                    .collect(Collectors.toList());

        }
        else
        {
            throw new ResourceNotFoundException("Some error occured!");
        }
        
    }

}
