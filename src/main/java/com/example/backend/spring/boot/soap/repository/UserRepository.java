package com.example.backend.spring.boot.soap.repository;

import com.example.backend.spring.boot.soap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
