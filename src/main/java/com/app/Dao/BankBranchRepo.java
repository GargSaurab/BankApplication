package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.Entity.BankBranch;

@Repository
public interface BankBranchRepo extends JpaRepository<BankBranch, Integer>{

}
