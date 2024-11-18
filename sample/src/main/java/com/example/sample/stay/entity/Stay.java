package com.example.sample.stay.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stayId;

    private String stayName;

    private String stayAddress;

    private String stayTel;

    private String checkIn;

    private String checkOut;

    private String price;

    private String stayType;
}
