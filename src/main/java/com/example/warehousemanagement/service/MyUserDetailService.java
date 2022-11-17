package com.example.warehousemanagement.service;

import com.example.warehousemanagement.model.MyUserDetails;
import com.example.warehousemanagement.model.User;
import com.example.warehousemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameIgnoreCase(userName);

        if (user == null){
            throw new UsernameNotFoundException("Username does not exist");
        }
        return new MyUserDetails(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user) {userRepository.save(user);}

    public void removeUserById(Integer id){userRepository.deleteById(id);}

    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found.."));
    }
}
