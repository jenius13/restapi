package com.kt.restapi.repository;

import com.kt.restapi.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    List<User> findAll();

}
