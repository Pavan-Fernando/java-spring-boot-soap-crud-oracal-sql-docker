package com.example.backend.spring.boot.soap.service;

import com.example.backend.spring.boot.soap.entity.User;

public interface UserService {

    User create(User user);

    User update(Long id, User user);

    void delete(Long id);

    User get(Long id);
}
