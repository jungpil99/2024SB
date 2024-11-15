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

    public List<TransPort> searchTransports(String departureTime, String arrivalTime, String departureCity, String arrivalCity, String transportType) {
        Specification<TransPort> spec = Specification.where(TransportSpecification.withDepartureTime(departureTime))
                .and(TransportSpecification.withArrivalTime(arrivalTime))
                .and(TransportSpecification.withDepartureCity(departureCity))
                .and(TransportSpecification.withArrivalCity(arrivalCity))
                .and(TransportSpecification.withTransportType(transportType));

        return transPortRepository.findAll(spec);
    }

    public List<TransPort> searchOneWay(String departureTime, String departureCity, String arrivalCity, String transportType) {
        Specification<TransPort> spec = Specification.where(TransportSpecification.withDepartureTime(departureTime))
                .and(TransportSpecification.withDepartureCity(departureCity))
                .and(TransportSpecification.withArrivalCity(arrivalCity))
                .and(TransportSpecification.withTransportType(transportType));

        return transPortRepository.findAll(spec);
    }

    public List<TransPort> searchNoArrive(String departureTime, String arrivalTime, String departureCity, String transportType) {
        Specification<TransPort> spec = Specification.where(TransportSpecification.withDepartureTime(departureTime))
                .and(TransportSpecification.withArrivalTime(arrivalTime))
                .and(TransportSpecification.withDepartureCity(departureCity))
                .and(TransportSpecification.withTransportType(transportType));

        return transPortRepository.findAll(spec);
    }
}
