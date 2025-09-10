package com.concurrency.concurrency_stock_order.repository;

import com.concurrency.concurrency_stock_order.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 테스트")
    public void createItem(){
        User user = new User("테스트 유저");
        User savedUser = userRepository.save(user);
        System.out.println(savedUser.toString());

    }
}