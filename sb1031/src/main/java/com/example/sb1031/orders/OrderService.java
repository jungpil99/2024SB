package com.example.sb1031.orders;


import com.example.sb1031.shipment.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ShipmentService shipmentService;

    public OrderService(ApplicationEventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        eventPublisher.publishEvent(new OrdersEvent(this,savedOrder));
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public void deleteOrder(Long id) {
        // 주문 ID로 주문을 조회
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            // 주문에 연관된 배송 정보 삭제
            if (order.getShipment() != null) {
                shipmentService.deleteShipmentByOrderId(id); // 배송 정보 삭제
            }

            // 주문 삭제
            orderRepository.delete(order);
        }
    }

}
