package com.app.Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.Entity.BankBranch;
import com.app.Entity.Transaction;
import com.app.Entity.TrscType;

@Repository
public class ProcedureRepo {

 @Autowired
 private JdbcTemplate jdbcTemplate;

 @Autowired
 private CustomerRepo custRepo;



//  public Customer getCustomerByTransaction

 public Integer getCustomer(int transactionId) {
        return jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement callableStatement = connection.prepareCall("call getcustomer(?,?)")) {
                callableStatement.setInt(1, transactionId);
                callableStatement.registerOutParameter(2, Types.INTEGER);
                callableStatement.execute();
                return callableStatement.getInt(2);
            }
        });
    }

    public Transaction getTransaction(int trscId)
     {
        return jdbcTemplate.execute("Call gettransaction(?)",
          (CallableStatementCallback<Transaction>) cs -> {
             cs.setInt(1, trscId);
             cs.execute();
             Transaction transaction = new Transaction();
             
             try(ResultSet rs = cs.getResultSet())
             {
                if(rs.next())
                {
                             transaction.setTrscId(rs.getInt("trsc_id"));
                            transaction.setAmount(rs.getDouble("amount"));
                            transaction.setTime((rs.getTimestamp("time")).toLocalDateTime());
                            transaction.setType(TrscType.valueOf(rs.getString("type")));
                           
        
                        }else
                {
                    return null;
                }
             }

             return transaction;
          });
     }

     public List<BankBranch> getBranchList() {
        return jdbcTemplate.execute(
            (CallableStatementCreator) connection -> {
                CallableStatement cs = connection.prepareCall("call getAllBranches()");
                return cs;
            },
            (CallableStatementCallback<List<BankBranch>>) cs -> {
                List<BankBranch> branches = new ArrayList<>();
                boolean hasResultSet = cs.execute();
                if (hasResultSet) {
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            BankBranch branch = new BankBranch();
                            branch.setBranchId(rs.getInt("branch_id"));
                            branch.setBranchName(rs.getString("branch_name"));
                            branch.setAddress(rs.getString("address"));
                            branch.setEmpNo(rs.getInt("emp_no"));
                            branch.setPhoneNumber(rs.getString("phone_number"));
                            branch.setEmail(rs.getString("email"));
                            branches.add(branch);
                        }
                    }
                }
                return branches;
            }
        );
    }
}
