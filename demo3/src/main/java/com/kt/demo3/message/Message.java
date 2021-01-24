package com.kt.demo3.message;

import lombok.Data;

@Data
public class Message {
    private Integer response;
//    private String message;
//    private Integer device_count;
    private Object data;


    public Message() {
        this.response = StatusEnum.BAD_REQUEST.statusCode;
        this.data = null;
//        this.message = null;
//        this.device_count = null;
    }
}
