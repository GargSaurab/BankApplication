package com.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.app.Entity.BankBranch;

@Repository
public interface BankBranchRepo extends JpaRepository<BankBranch, Integer>{

    @Procedure("getAllBranches")
    List<BankBranch> getBranchList();

}
