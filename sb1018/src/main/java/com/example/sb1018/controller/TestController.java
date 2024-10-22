package com.example.sb1018.controller;

import com.example.sb1018.entity.Dept;
import com.example.sb1018.entity.Emp;
import com.example.sb1018.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

//    @Autowired
    final EntityService entityService; //생성자 주입 방식

    @GetMapping("/{deptNo}/{deptName}")
    public Dept index(@PathVariable Integer deptNo, @PathVariable String deptName) {
        return entityService.updateDept(deptNo,deptName);
    }

    @GetMapping("/test")
    public List<Emp> index2(){
        return entityService.selectAll();
    }
}
