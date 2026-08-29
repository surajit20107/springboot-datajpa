package com.springDataJpa.service;

import com.springDataJpa.entities.Order;
import com.springDataJpa.entities.User;
import com.springDataJpa.repository.OrderRepository;
import com.springDataJpa.repository.UserRepository;
import dto.CreateOrderDto;
import dto.OrderDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDto createOrder(Long userid, CreateOrderDto createOrderDto) {
        User user = userRepository.findById(userid).orElseThrow();
        Order order = new Order();
        order.setUser(user);
        order.setProductName(createOrderDto.getProductName());

        Order createdOrder = orderRepository.save(order);
        return new OrderDto(createdOrder.getId(), createdOrder.getProductName(), createdOrder.getUser());
    }

    public List<OrderDto> getOrdersById(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtos = new ArrayList<>();
        
        orders.forEach(order -> {
            OrderDto orderDto = new OrderDto(order.getId(), order.getProductName(), order.getUser());
            orderDtos.add(orderDto);
        });

        return orderDtos;
    }
}
