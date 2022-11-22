package com.example.warehousemanagement.service;

import com.example.warehousemanagement.model.Item;
import com.example.warehousemanagement.model.Order;
import com.example.warehousemanagement.model.StatusType;
import com.example.warehousemanagement.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public void addOrder(Order order){
        orderRepository.save(order);
    }

    public void removeOrderByOrderNumber(Integer orderNumber){
        orderRepository.deleteById(orderNumber);
    }

    public void removeAllOrders(List<Order> orders){
        orderRepository.deleteAll();
    }

    public Order getOrderByOrderNumber(Integer orderNumber){
        return orderRepository.findById(orderNumber).orElseThrow(() -> new RuntimeException("Order not found.."));
    }

    public List<Order> getAllOrdersByStatus(StatusType status){
        return orderRepository.getAllOrdersByStatus(status);
    }

    public Order getOrderByItemName(String itemName){
        return orderRepository.getOrderByItemName(itemName);
    }

}
