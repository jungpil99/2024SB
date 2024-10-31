package com.example.sb1031.orders;

import com.example.sb1031.shipment.Shipment;
import com.example.sb1031.shipment.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @Autowired
    ShipmentService shipmentService;

    @EventListener
    public void handleOrdersEvent(OrdersEvent event) {
        Order order = event.getOrder();

        Shipment shipment = new Shipment();
        shipment.setProductName(order.getProductName());
        shipment.setQuantity(order.getQuantity());
        shipment.setPrice(order.getPrice());
        shipment.setStatus("주문 접수");

        shipmentService.saveShipment(shipment);

        order.setShipment(shipment);
    }

    @EventListener
    public void handleOrdersDeleteEvent(OrdersEvent event) {
        Long orderId = event.getOrderId();
        // 주문 ID에 해당하는 배송 정보 삭제
        shipmentService.deleteShipmentByOrderId(orderId);
    }
}
