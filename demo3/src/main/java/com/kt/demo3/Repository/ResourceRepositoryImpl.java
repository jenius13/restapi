//package com.kt.demo3.Repository;
//
//import com.kt.demo3.domain.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Set;
//
//@Repository
//public class ResourceRepositoryImpl implements ResourceRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public ResourceRepositoryImpl(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }
//
//    public Set<Resource> findre(Integer device_seq) {
//        String sql = "select * from resources where device_seq = ?";
//            return jdbcTemplate.queryForObject(sql,new Object[]{device_seq},resourceMapper);
//
//
//    static RowMapper<Resource> resourceMapper = (rs, rowNum)-> new Resource(
//            rs.getLong("resource_id"),
//            rs.getLong("device_seq"),
//            rs.getString("group"),
//            rs.getString("code"),
//            rs.getBoolean("value")
//    );
//
//
//}
