package com.fzu;

import com.fzu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RuangongHoutaiApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

}
