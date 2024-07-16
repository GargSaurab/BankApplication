package com.app.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.Entity.BankEmployee;
import com.app.Entity.JobTitle;

@Repository
public class BankEmployeeJdbcRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(BankEmployee employee)
    {
        String sql = "Insert Into bank_employee (name, job_title, password, role) values (?, ?, ?, ?)";

        jdbcTemplate.update(sql, new Object[] {employee.getName(), employee.getJobTitle(), employee.getPassword(), employee.getRole()});
    }

    public void deleteById(int id)
    {
         String sql = "delete from bank_employee where id = ?";

         jdbcTemplate.update(sql,id);
    }
     
    public Optional<BankEmployee> findById(int id)
    {
        String sql = "select * from bank_employee  where id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
               Optional.of(new BankEmployee(
                rs.getInt("id"),
                rs.getString("name"),
                JobTitle.valueOf(rs.getString("job_title")),
                rs.getString("password"),
                rs.getString("role")
               )), id
               );
    }

    public List<BankEmployee> findAll()
    {
        String sql = "select * from bank_employee  where id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
              new BankEmployee(
                rs.getInt("id"),
                rs.getString("name"),
                JobTitle.valueOf(rs.getString("job_title")),
                rs.getString("password"),
                rs.getString("role")
               )
               );
    }


}
