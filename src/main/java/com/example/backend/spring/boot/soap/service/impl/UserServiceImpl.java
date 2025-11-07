package com.example.backend.spring.boot.soap.service.impl;

import com.example.backend.spring.boot.soap.entity.User;
import com.example.backend.spring.boot.soap.repository.UserRepository;
import com.example.backend.spring.boot.soap.service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final Validator validator;

    public UserServiceImpl(UserRepository repo, Validator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    @Override
    public User create(User user) {
        validate(user);
        return repo.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existing = repo.findById(id).orElseThrow();
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        validate(existing);
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public User get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    private void validate(User u) {
        var violations = validator.validate(u);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
