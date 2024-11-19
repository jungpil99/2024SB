package com.example.sample.stay.controller;

import com.example.sample.stay.entity.Stay;
import com.example.sample.transport.entity.TransPort;
import org.springframework.data.jpa.domain.Specification;

public class StaySpecification {

    public static Specification<Stay> withStayType(String stayType) {
        return (root, query, builder) ->
                (stayType == null || stayType.equals("all"))
                        ? null
                        : builder.equal(root.get("stayType"), stayType);
    }

    //주소 검색 조건
    public static Specification<Stay> withStayAddress(String stayAddress) {
        return (root, query, builder) ->
                (stayAddress == null || stayAddress.isEmpty())
                        ? null
                        : builder.like(root.get("stayAddress"), "%" + stayAddress + "%");
    }
}
