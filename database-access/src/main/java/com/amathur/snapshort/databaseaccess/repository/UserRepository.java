package com.amathur.snapshort.databaseaccess.repository;

import com.amathur.snapshort.databaseaccess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>
{
    boolean existsByUsername(String username);
}
