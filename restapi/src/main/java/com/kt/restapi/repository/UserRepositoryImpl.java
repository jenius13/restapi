package com.kt.restapi.repository;

import com.kt.restapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from account";
        return jdbcTemplate.query(sql, userMapper);
    }

    static RowMapper<User> userMapper = (rs,rowNum) -> new User(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getDate("createdAt")
    );


}
