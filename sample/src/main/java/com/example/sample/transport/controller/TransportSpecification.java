package com.example.sample.transport.controller;

import com.example.sample.transport.entity.TransPort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransportSpecification {

    public static Specification<TransPort> withTransportType(String transportType) {
        return ((root, query, builder) ->
                transportType.equals("all") ? null : builder.equal(root.get("transportType"), transportType));
    }

    // departureTime을 LocalDateTime으로 비교하도록 수정
    public static Specification<TransPort> withDepartureTime(String departureTimeStr) {
        return ((root, query, builder) -> {
            if (departureTimeStr == null) {
                return null;
            }

            LocalDateTime startOfDay = LocalDate.parse(departureTimeStr).atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

            return builder.and(
                    builder.greaterThanOrEqualTo(root.get("departureTime"), startOfDay),
                    builder.lessThanOrEqualTo(root.get("departureTime"), endOfDay)
            );
        });
    }



    // arrivalTime을 LocalDateTime으로 비교하도록 수정
    public static Specification<TransPort> withArrivalTime(String arrivalTimeStr) {
        return ((root, query, builder) -> {
            if (arrivalTimeStr == null) {
                return null;
            }
            // 입력된 String을 LocalDateTime으로 변환
            LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr + "T00:00:00");
            return builder.greaterThanOrEqualTo(root.get("arrivalTime"), arrivalTime);
        });
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
