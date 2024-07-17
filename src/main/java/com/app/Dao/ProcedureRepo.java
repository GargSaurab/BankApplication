package com.app.Dao;

import java.sql.Types;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class ProcedureRepo {

 @Autowired
 private JdbcTemplate jdbcTemplate;

 private SimpleJdbcCall getCustomer;

 @PostConstruct
 public void init()
 {
     getCustomer = new SimpleJdbcCall(jdbcTemplate)
             .withProcedureName("getcustomer")
             .declareParameters(
                  new SqlParameter("trsc_id", Types.INTEGER),
                  new SqlOutParameter("customer_id", Types.INTEGER)
             );
 }

 public int getcustomer(int id)
 {
        Map<String,Object> out = getCustomer.execute(id);

        return (int) out.get("customer_id");
 }

}
