package com.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.Entity.Customer;
import com.app.Entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Integer>{


     List<Transaction> findAllByCustomer(Customer customer);

     //Calling the procedure stored in database
     @Procedure( "getcustomer")
     int getcustomer(@Param("transaction_id") int transactionId);
}

/*
 * CREATE OR REPLACE PROCEDURE getcustomer(
    IN transaction_id int
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
    FROM transaction t
    WHERE t.trsc_id = transaction_id;
END;
$$;

*/
