package com.example.sample.stay.service;

import com.example.sample.stay.controller.StaySpecification;
import com.example.sample.stay.entity.Stay;
import com.example.sample.stay.repository.StayRepository;
import com.example.sample.transport.controller.TransportSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StayService {

    @Autowired
    StayRepository stayRepository;

    public List<Stay> SearchByStay(String stayType, String stayAddress){
        Specification<Stay> spec = Specification.where(null);

        if (stayType != null && !stayType.equals("all")) {
            spec = spec.and(StaySpecification.withStayType(stayType));
        }

        if(stayAddress != null && !stayAddress.isEmpty()){
            spec = spec.and(StaySpecification.withStayAddress(stayAddress));
        }

        return stayRepository.findAll(spec);
    }

    public List<Stay> SearchByType(String stayType){
        Specification<Stay> spec = Specification.where(null);

        if (stayType != null && !stayType.equals("all")) {
            spec = spec.and(StaySpecification.withStayType(stayType));
        }

        return stayRepository.findAll(spec);
    }
}
