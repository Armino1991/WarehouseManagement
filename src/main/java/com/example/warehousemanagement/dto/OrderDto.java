package com.example.warehousemanagement.dto;

import com.example.warehousemanagement.model.StatusType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OrderDto {

    private int orderNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date submittedDate;
    private StatusType status;
    private String itemName;
    private ItemDto itemDto;
    private int itemId;
    private int itemQuantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadlineDate;

    private String comment;

}
