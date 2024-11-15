package com.example.sample.transport.repository;

import com.example.sample.transport.entity.TransPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransPortRepository extends JpaRepository<TransPort, Integer>, JpaSpecificationExecutor<TransPort> {

}
