package com.example.warehousemanagement.controller;

import com.example.warehousemanagement.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {

    @Autowired
    ItemService itemService;

    @GetMapping("/manager")
    public String managerHome(){
        return "managerHome";
    }

    @GetMapping("/manager/items")
    public String getItems(Model model){
        model.addAttribute("users", itemService.getAllItems());
        return "items";
    }
}
