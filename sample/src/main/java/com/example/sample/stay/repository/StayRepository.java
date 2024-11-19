package com.example.sample.stay.repository;

import com.example.sample.stay.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StayRepository extends JpaRepository<Stay, Integer>, JpaSpecificationExecutor<Stay> {
}
