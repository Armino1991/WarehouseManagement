package com.example.warehousemanagement.service;

import com.example.warehousemanagement.model.MyUserDetails;
import com.example.warehousemanagement.model.User;
import com.example.warehousemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserUsernameIgnoreCase(username);

        if (user == null){
            throw new UsernameNotFoundException("Username does not exist");
        }
        return new MyUserDetails(user);
    }
}
