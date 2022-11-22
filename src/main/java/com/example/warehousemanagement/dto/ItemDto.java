package com.example.warehousemanagement.dto;

import lombok.Data;

@Data
public class ItemDto {

    private int id;
    private String itemName;
    private double price;
    private int quantity;
}
