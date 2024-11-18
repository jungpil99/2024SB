package com.example.sample.transport.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TransPort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transId;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private String departureCity;

    private String arrivalCity;

    private String transportType;

    private Integer price;

}
