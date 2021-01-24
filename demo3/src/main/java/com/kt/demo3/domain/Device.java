package com.kt.demo3.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Device {

    private Integer device_seq;

    private Integer user_id;
    private String company_name;
    private Integer service_target_seq;
    private String external;
    private String model_id;
    private int model_type_code;
    private String device_name;
    private List<Resource> resources;

    public Device(Integer device_seq, Integer user_id, String company_name, Integer service_target_seq, String external, String model_id, int model_type_code, String device_name) {
        this.device_seq = device_seq;
        this.user_id = user_id;
        this.company_name = company_name;
        this.service_target_seq = service_target_seq;
        this.external = external;
        this.model_id = model_id;
        this.model_type_code = model_type_code;
        this.device_name = device_name;
        this.resources = null;
    }
}
