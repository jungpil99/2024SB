package com.example.sample.review.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewView {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private Integer reviewId;
    private String viewDate;

    public ReviewView(String username, Integer reviewId, String viewDate) {
        this.username = username;
        this.reviewId = reviewId;
        this.viewDate = viewDate;
    }
}
