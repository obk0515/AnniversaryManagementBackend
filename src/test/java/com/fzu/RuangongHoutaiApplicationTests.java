package com.fzu;

import com.fzu.entity.User;
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
        User user=new User();
        user.setId("8");
        user.setSex("1");
        userService.updateById(user);
    }

}
