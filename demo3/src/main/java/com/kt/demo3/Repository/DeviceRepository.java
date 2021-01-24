package com.kt.demo3.Repository;

import com.kt.demo3.domain.Device;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeviceRepository {
    List<Device> findAll();

    Device findById(Integer device_seq);

    Integer getCountdevice(Integer device_seq);

    List<Device> findByuserId(Integer user_id);

    Device findByDeviceFields(Map fields);

    Object[] modifyDeviceResourceValue(Map fields);

}
