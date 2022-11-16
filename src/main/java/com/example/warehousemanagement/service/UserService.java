package com.example.warehousemanagement.service;

import com.example.warehousemanagement.model.User;
import com.example.warehousemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user) {userRepository.save(user);}

    public void removeUserById(Integer id){userRepository.deleteById(id);}

    public void removeAllUsers(List<User> users){userRepository.deleteAll();}

    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found.."));
    }

    public List<User> findAllById(int id){
        return userRepository.findAllById();
    }
}
