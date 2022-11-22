package com.example.warehousemanagement.repository;

import com.example.warehousemanagement.model.Order;
import com.example.warehousemanagement.model.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderNumber(Integer orderNumber);

    List<Order> getAllOrdersByStatus(StatusType status);

    Order getOrderByItemName(String itemName);

}
