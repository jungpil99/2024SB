package com.example.sample.trip.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripId;

    private String city;

    private Integer packageId;

    private String packageName;

    private String departureDate;

}
