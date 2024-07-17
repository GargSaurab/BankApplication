package com.app.Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class ProcedureRepo {

 @Autowired
 private JdbcTemplate jdbcTemplate;

 private SimpleJdbcCall getCustomer;

//  @PostConstruct
//  public void init()
//  {
//      getCustomer = new SimpleJdbcCall(jdbcTemplate)
//              .withProcedureName("getcustomer"
//              .declareParameters(
//                   new SqlParameter("trsc_id", Types.INTEGER),
//                   new SqlOutParameter("customer_id", Types.INTEGER)
//              ));
//  }

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

}
