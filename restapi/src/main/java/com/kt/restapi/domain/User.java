package com.kt.restapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


public class User {
    private int user_id;
    private String user_name;
    private Date createdAt;

    public User(int user_id, String user_name, Date createdAt) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.createdAt = createdAt;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
