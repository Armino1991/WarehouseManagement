package com.example.warehousemanagement.repository;

import com.example.warehousemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    List<User> findAllById();

    User findByUserUsernameIgnoreCase(String name);
}
