package com.example.sb1018.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Dept {

    @Id
    private Integer deptno;
    private String dname;
    private String loc;
}
