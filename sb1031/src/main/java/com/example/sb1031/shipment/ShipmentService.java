package com.example.sb1031.shipment;


import com.example.sb1031.orders.Order;
import com.example.sb1031.orders.OrdersEvent;
import com.example.sb1031.shipment.Shipment;
import com.example.sb1031.shipment.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Shipment saveShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    public void updateShipmentStatus(Long id, String status) {
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(id);
        if (shipmentOptional.isPresent()) {
            Shipment shipment = shipmentOptional.get();
            shipment.setStatus(status);
            shipmentRepository.save(shipment);
        }
    }

    public void deleteShipmentByOrderId(Long orderId) {
        // 배송 정보를 삭제하는 로직 (배송 테이블에서 주문 ID로 검색)
        shipmentRepository.deleteByOrderId(orderId);
    }
}
