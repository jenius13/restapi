package com.kt.demo3.domain;

import com.kt.demo3.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Resource{
    private Long resource_id;
    private Long device_seq;
    private String group;
    private String code;
    private boolean value;

    public Resource(Long resource_id, Long device_seq, String group, String code, boolean value) {
        this.resource_id = resource_id;
        this.device_seq = device_seq;
        this.group = group;
        this.code = code;
        this.value = value;
    }


}
