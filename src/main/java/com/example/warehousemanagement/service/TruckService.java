package com.example.warehousemanagement.service;

import com.example.warehousemanagement.model.Item;
import com.example.warehousemanagement.model.Truck;
import com.example.warehousemanagement.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;

    public List<Truck> getAllTrucks(){
        return truckRepository.findAll();
    }

    public void addTruck(Truck truck){
        truckRepository.save(truck);
    }

    public void removeTruckById(Integer id){
        truckRepository.deleteById(id);
    }

    public void removeAllTrucks(List<Truck> trucks){
        truckRepository.deleteAll();
    }

    public Truck getTruckById(Integer id){
        return truckRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found.."));
    }
}
