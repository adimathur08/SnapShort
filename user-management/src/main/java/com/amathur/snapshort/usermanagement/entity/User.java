package com.amathur.snapshort.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String hashedPassword;
}
