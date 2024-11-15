package com.example.sample.transport.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransPort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transId;

    private String departureTime;

    private String arrivalTime;

    private String departureCity;

    private String arrivalCity;

    private String transportType;

    private String link;

}
