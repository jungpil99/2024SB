package com.example.sb1031.shipment;


import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Transactional
    void deleteByOrderId(Long orderId);
}
