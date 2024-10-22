package com.example.sb1015_2;

import com.example.sb1015_2.entity.MyData;
import com.example.sb1015_2.repository.MyDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Sb10152ApplicationTests {

    @Autowired
    MyDataRepository repository;

    @Test
    void Data_List() {
        List<MyData> list = repository.findAll();
        for (MyData myData : list) {
            System.out.println(myData);
        }
    }

}
