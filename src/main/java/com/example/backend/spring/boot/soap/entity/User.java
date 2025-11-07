package com.example.backend.spring.boot.soap.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z\\s\\-]+$", message = "Name must contain only letters, spaces, hyphens")
    @Column(nullable = false)
    private String name;

    @NotBlank @Email @Size(max = 100)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\+94[0-9]{9}|[0-9]{10})$", message = "Phone must be +94XXXXXXXXX or 10 digits")
    @Column(nullable = false, unique = true)
    private String phone;
}
