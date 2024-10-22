package com.example.chap17;

import com.example.chap17.dao.IArticaleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Chap17ApplicationTests {

    @Autowired
    IArticaleDao articeDao;

    @Test
    void contextLoads() {
        System.out.println(articeDao.selectCount());
    }

}
