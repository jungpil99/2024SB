package com.example.sample.transport.service;

import com.example.sample.transport.controller.TransportSpecification;
import com.example.sample.transport.entity.TransPort;
import com.example.sample.transport.repository.TransPortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransPortService {

    @Autowired
    TransPortRepository transPortRepository;

    public List<TransPort> searchTransports(String departureTime, String departureCity, String transportType) {
        Specification<TransPort> spec = Specification.where(null);

        // 각 조건에 대해 null이나 빈 값 체크 후 필터 추가
        if (departureTime != null && !departureTime.isEmpty()) {
            spec = spec.and(TransportSpecification.withDepartureTime(departureTime));
        }

        if (departureCity != null && !departureCity.isEmpty()) {
            spec = spec.and(TransportSpecification.withDepartureCity(departureCity));
        }

        if (transportType != null && !transportType.equals("all")) {
            spec = spec.and(TransportSpecification.withTransportType(transportType));
        }

        return transPortRepository.findAll(spec);
    }

    public List<TransPort> searchByDepartTime(String departureTime, String transportType) {
        Specification<TransPort> spec = Specification.where(null);

        // 각 조건에 대해 null이나 빈 값 체크 후 필터 추가
        if (departureTime != null && !departureTime.isEmpty()) {
            spec = spec.and(TransportSpecification.withDepartureTime(departureTime));
        }

        if (transportType != null && !transportType.equals("all")) {
            spec = spec.and(TransportSpecification.withTransportType(transportType));
        }

        return transPortRepository.findAll(spec);
    }

    public List<TransPort> searchByDepartCity(String departureCity, String transportType) {
        Specification<TransPort> spec = Specification.where(null);

        if (departureCity != null && !departureCity.isEmpty()) {
            spec = spec.and(TransportSpecification.withDepartureCity(departureCity));
        }

        if (transportType != null && !transportType.equals("all")) {
            spec = spec.and(TransportSpecification.withTransportType(transportType));
        }

        return transPortRepository.findAll(spec);
    }

    public List<TransPort> searchByTransType(String transportType) {
        Specification<TransPort> spec = Specification.where(null);

        if (transportType != null && !transportType.equals("all")) {
            spec = spec.and(TransportSpecification.withTransportType(transportType));
        }

        return transPortRepository.findAll(spec);
    }
}
