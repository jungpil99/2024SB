package com.example.sb1031.orders;


import com.example.sb1031.event.CustomEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CustomEventPublisher customEventPublisher;
    private final OrderService orderService;

    @PostMapping
    @ResponseBody
    public Order createOrder(@RequestBody Order order) {

        return orderService.saveOrder(order);
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "/orders/orderList";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteOrder(@PathVariable Long id) {
        customEventPublisher.publishOrderEvent(new OrdersEvent(this, id));
        orderService.deleteOrder(id);
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("orders", new Order());
        return "/orders/orderForm";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute Order order) {
        log.info("Order created: " + order);
        OrdersEvent event = new OrdersEvent(this, order);
        customEventPublisher.doStuffAndPublishAnEvent(order.toString());
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder1(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}