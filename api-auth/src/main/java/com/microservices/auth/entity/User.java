package com.microservices.auth.entity;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
}
