package com.example.warehousemanagement.repository;

import com.example.warehousemanagement.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Integer> {

    Truck findByChNumber(String chNumber);
}
