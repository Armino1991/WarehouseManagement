package com.example.warehousemanagement.repository;

import com.example.warehousemanagement.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

   // Item findByName (String itemName);

   //Item findByItemName (String itemName);
}
