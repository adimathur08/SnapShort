package com.amathur.snapshort.databaseaccess.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "\"user\"")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @Column(nullable = false)
    String username;
    String password;
}
