package com.example.warehousemanagement.service;

import com.example.warehousemanagement.model.Item;
import com.example.warehousemanagement.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public void removeItemById(Integer id){
        itemRepository.deleteById(id);
    }

    public void removeAllItems(List<Item> items){
        itemRepository.deleteAll();
    }

    public Item getItemById(Integer id){
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found.."));
    }
}
