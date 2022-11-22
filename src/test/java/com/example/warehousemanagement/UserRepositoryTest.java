package com.example.warehousemanagement;

import com.example.warehousemanagement.model.User;
import com.example.warehousemanagement.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest(){
        User user = User.builder()
                .userId(1)
                .username("client1")
                .password("1234")
                .role("CLIENT")
                .build();
        userRepository.save(user);

        Assertions.assertThat(user.getUserId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getUserTest(){
        User user = userRepository.findById(1).get();
        Assertions.assertThat(user.getUserId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getListOfUsersTest(){
        List<User> users = userRepository.findAll();

        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest(){
        User user = userRepository.findById(1).get();

        user.setUsername("client2");

        User userUpdated = userRepository.save(user);
        Assertions.assertThat(userUpdated.getUsername()).isEqualTo("client1");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserTest(){
        User user = userRepository.findById(1).get();

        userRepository.delete(user);

        User user1 = null;
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsernameIgnoreCase("client1"));
        if (optionalUser.isPresent()){
            user1 = optionalUser.get();
        }

        Assertions.assertThat(user1).isNull();
    }
}
