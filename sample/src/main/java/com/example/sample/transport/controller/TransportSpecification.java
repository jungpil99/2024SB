package com.example.sample.transport.controller;

import com.example.sample.transport.entity.TransPort;
import org.springframework.data.jpa.domain.Specification;

public class TransportSpecification {

    public static Specification<TransPort> withTransportType(String transportType) {
        return ((root, query, builder) ->
                transportType.equals("all") ? null : builder.equal(root.get("transportType"), transportType));
    }

    public static Specification<TransPort> withDepartureTime(String departureTime) {
        return ((root, query, builder) ->
                departureTime == null ? null : builder.equal(root.get("departureTime"), departureTime));
    }

    public static Specification<TransPort> withArrivalTime(String arrivalTime) {
        return ((root, query, builder) ->
                arrivalTime == null ? null : builder.equal(root.get("arrivalTime"), arrivalTime));
    }

    public static Specification<TransPort> withDepartureCity(String departureCity) {
        return ((root, query, builder) ->
                departureCity == null ? null : builder.equal(root.get("departureCity"), departureCity));
    }

    public static Specification<TransPort> withArrivalCity(String arrivalCity) {
        return ((root, query, builder) ->
                arrivalCity == null ? null : builder.equal(root.get("arrivalCity"), arrivalCity));
    }
}
