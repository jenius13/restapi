package com.kt.demo3.Repository;

import com.kt.demo3.domain.Device;
import com.kt.demo3.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {
    //                    rs.getInt("device_seq"),
//                    rs.getInt("user_id"),
//                    rs.getString("company_name"),
//                    rs.getInt("service_target_seq"),
//                    rs.getString("external"),
//                    rs.getString("model_id"),
//                    rs.getInt("model_type_code"),
//                    rs.getString("device_name")
    private static final String SQL_FIND_COUNT_BY_DEVICE_SEQ = "SELECT COUNT(*) " +
            "FROM DEVICE WHERE DEVICE_SEQ = ?";

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DeviceRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Device> findAll() {
        String sql = "select * from device";
        return jdbcTemplate.query(sql, deviceMapper);
    }


//    @Override
//    public List<Devices> findAlldevice() {
//        String sql = "select * from device";
//        return jdbcTemplate.query(sql,deviceMapper);
//    }

    //Test Code ////
    //   @Override
//    public List<Resource> findByuserId(Integer user_id){
//        String sql = "select * from device where user_id = ?";
//        Device device = jdbcTemplate.queryForObject(sql,new Object[] {user_id},deviceMapper);
//        ArrayList<Integer> test = new ArrayList<>();
//        for (int i = 0; i < test.size() ; i++){
//            test.add(device.getDevice_seq());
//        }
//        System.out.println(test);
//        String sql2 = "select * from resources where deviceseq in device_seq";
//        List<Resource> resource = jdbcTemplate.query(sql2,resourceMapper);
//        return resource;
//
//    }


    public List<Device> findByuserId(Integer user_id) {
        String sql = "select * from device where user_id = ?";
        // Device device = jdbcTemplate.queryForObject(sql,new Object[] {user_id},deviceMapper);
        //jdbcTemplate.queryForObject()
        List<Device> devices = jdbcTemplate.query(sql, new Object[]{user_id}, deviceMapper);

        for (Device device : devices) {
            String sql2 = "select * from resources where device_seq = ?";
            List<Resource> resources = jdbcTemplate.query(sql2, new Object[]{device.getDevice_seq()}, resourceMapper);
            device.setResources(resources);
        }


        return devices;

    }

    public Device findByDeviceFields(Map fields) {

        Integer device_seq = (Integer) fields.get("device_seq");
        Integer service_target_seq = (Integer) fields.get("service_target_seq");
        String external = fields.get("external").toString();
        String model_id =  fields.get("model_id").toString();
        Integer model_type_code = (Integer) fields.get("model_type_code");
        String device_name =  fields.get("device_name").toString();

        String sql = "select * from device where device_seq = ? AND service_target_seq = ?" +
                " AND external= ? AND model_id = ? AND model_type_code = ? AND device_name = ?";
        // Device device = jdbcTemplate.queryForObject(sql,new Object[] {user_id},deviceMapper);
        //jdbcTemplate.queryForObject()

        Device device = null;
        Object[] args = {device_seq, service_target_seq, external, model_id, model_type_code, device_name};
        for (Object arg: args     ) {
            System.out.println(arg);
        }
        try{
            device = jdbcTemplate.queryForObject(sql,
                    args, deviceMapper);
            String sql2 = "select * from resources where device_seq = ?";


            List<Resource> resources = jdbcTemplate.query(sql2, new Object[]{device.getDevice_seq()}, resourceMapper);
            device.setResources(resources);
        }catch (EmptyResultDataAccessException e){

        }


        return device;

    }

public Object[] modifyDeviceResourceValue(Map fields) {

        Integer device_seq = (Integer) fields.get("device_seq");
        Integer service_target_seq = (Integer) fields.get("service_target_seq");
        String external = fields.get("external").toString();
        String model_id =  fields.get("model_id").toString();
        Integer model_type_code = (Integer) fields.get("model_type_code");
        String device_name =  fields.get("device_name").toString();

        String sql = "select * from device where device_seq = ? AND service_target_seq = ?" +
                " AND external= ? AND model_id = ? AND model_type_code = ? AND device_name = ?";
        // Device device = jdbcTemplate.queryForObject(sql,new Object[] {user_id},deviceMapper);
        //jdbcTemplate.queryForObject()

        Device device = null;
        Object[] args = {device_seq, service_target_seq, external, model_id, model_type_code, device_name};
        for (Object arg: args     ) {
            System.out.println(arg);
        }
        try{
            device = jdbcTemplate.queryForObject(sql,
                    args, deviceMapper);
            String sql2 = "select * from resources where device_seq = ?";


            List<Resource> resources = jdbcTemplate.query(sql2, new Object[]{device.getDevice_seq()}, resourceMapper);
            Map res = (Map) fields.get("resource");

            for(Resource resource: resources){
                if(resource.getCode().equals(res.get("code")) &&
                        resource.getGroup().equals(res.get("group"))){

                    if(resource.isValue()!= Boolean.parseBoolean(res.get("value").toString())){
                        // db value is different and has to be change and update log table

                        String updateSql = "UPDATE resources SET value = ? WHERE code = ? AND \"group\" = ? AND resource_id= ?";
                        boolean newValue = !resource.isValue();
                        final int update = jdbcTemplate.update(updateSql, new Object[]{newValue, resource.getCode(), resource.getGroup(), resource.getResource_id()});

                        String insertLogSql = "INSERT INTO logs (resource_id, value, \"createdAt\") VALUES (?,?, ?)";
                        jdbcTemplate.update(insertLogSql, new Object[]{resource.getResource_id(), newValue, new Date()});
                        resource.setValue(newValue);
                        return new Object[]{true, resource};

                    }


                    return new Object[]{false, resource};
                }

            }

            //device.setResources(resources);
        }catch (EmptyResultDataAccessException e){

        }


        return null;

    }


    @Override
    public Device findById(Integer device_seq) {
//        String sql = "select d.device_seq,d.user_id,d.company_name,d.service_target_seq,d.external,d.model_id," +
//                "d.model_type_code,d.device_name from device as d inner join resources " +
//                "as r on d.device_seq = r.device_seq where d.device_seq = ?";
        String sql = "select * from device where device_seq = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{device_seq}, deviceMapper);
    }

    @Override
    public Integer getCountdevice(Integer device_seq) {

        return jdbcTemplate.queryForObject(SQL_FIND_COUNT_BY_DEVICE_SEQ, new Object[]{device_seq}, Integer.class);
    }

    static RowMapper<Resource> resourceMapper = (rs, rowNum) -> new Resource(
            rs.getLong("resource_id"),
            rs.getLong("device_seq"),
            rs.getString("group"),
            rs.getString("code"),
            rs.getBoolean("value")
    );

    static RowMapper<Device> deviceMapper = (rs, rowNum) -> new Device(
            rs.getInt("device_seq"),
            rs.getInt("user_id"),
            rs.getString("company_name"),
            rs.getInt("service_target_seq"),
            rs.getString("external"),
            rs.getString("model_id"),
            rs.getInt("model_type_code"),
            rs.getString("device_name")
//            rs.getInt("resource_id"),
//            rs.getString("group"),
//            rs.getString("code"),
//            rs.getBoolean("value")

    );

    private class TEST {
    }
}
