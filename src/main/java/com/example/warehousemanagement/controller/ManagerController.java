package com.example.warehousemanagement.controller;

import com.example.warehousemanagement.dto.ItemDto;
import com.example.warehousemanagement.dto.OrderDto;
import com.example.warehousemanagement.dto.TruckDto;
import com.example.warehousemanagement.model.*;
import com.example.warehousemanagement.service.ItemService;
import com.example.warehousemanagement.service.OrderService;
import com.example.warehousemanagement.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

import static com.example.warehousemanagement.model.StatusType.*;

@Controller
public class ManagerController extends BaseController{

    @Autowired
    ItemService itemService;

    @Autowired
    TruckService truckService;

    @Autowired
    OrderService orderService;

    @GetMapping("/manager")
    public String managerHome(){
        return "managerHome";
    }

    @GetMapping("/manager/items")
    public String getItems(Model model){
        model.addAttribute("items", itemService.getAllItems());
        return "items";
    }

    @GetMapping("/manager/items/add")
    public String getUserAdd(Model model){
        model.addAttribute("itemDto", new ItemDto());
        return "itemsAdd";
    }
    @PostMapping("/manager/items/add")
    public String postItemAdd(@ModelAttribute("itemDto") ItemDto itemDto){
        Item item = new Item();

        item.setId(itemDto.getId());
        item.setName(itemDto.getItemName());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        itemService.addItem(item);

        return "redirect:/manager/items";
    }
    @GetMapping("/manager/items/delete/{id}")
    public String deleteItem(@PathVariable int id){
        itemService.removeItemById(id);
        return "redirect:/manager/items";
    }
    @GetMapping("/manager/items/update/{id}")
    public String updateItem(@PathVariable int id, Model model){
        Item item = itemService.getItemById(id);
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setItemName(item.getName());
        itemDto.setQuantity(item.getQuantity());

        model.addAttribute("itemDto", itemDto);
        itemService.removeItemById(id);

        return "itemsAdd";
    }
    @GetMapping("/manager/trucks")
    public String getTrucks(Model model){
        model.addAttribute("trucks", truckService.getAllTrucks());
        return "trucks";
    }
    @GetMapping("/manager/trucks/add")
    public String getTruckAdd(Model model){
        model.addAttribute("truckDto", new TruckDto());
        return "trucksAdd";
    }
    @PostMapping("/manager/trucks/add")
    public String postTruckAdd(@ModelAttribute("truckDto") TruckDto truckDto){
        Truck truck = new Truck();

        truck.setTruckId(truckDto.getId());
        truck.setChNumber(truckDto.getChNumber());
        truck.setLPlate(truckDto.getLPlate());
        truckService.addTruck(truck);

        return "redirect:/manager/trucks";
    }
    @GetMapping("/manager/trucks/delete/{id}")
    public String deleteTruck(@PathVariable int id){
        truckService.removeTruckById(id);
        return "redirect:/manager/trucks";
    }
    @GetMapping("/manager/trucks/update/{id}")
    public String updateTrucks(@PathVariable int id, Model model){

        Truck truck = truckService.getTruckById(id);
        TruckDto truckDto = new TruckDto();
        truckDto.setId(truck.getTruckId());
        truckDto.setChNumber(truck.getChNumber());
        truckDto.setLPlate(truck.getLPlate());

        model.addAttribute("truckDto", truckDto);
        truckService.removeTruckById(id);

        return "trucksAdd";
    }
    @GetMapping("/manager/orders")
    public String getManagerOrders(Model model){
        model.addAttribute("orders", orderService.getAllOrders());
        return "viewManagerOrders";
    }
    @GetMapping("/manager/order/{status}")
    public String viewOrderByStatus(Model model, @PathVariable StatusType status){
        model.addAttribute("orders", orderService.getAllOrdersByStatus(status));
        return "viewManagerOrders";
    }
    @GetMapping("/manager/viewManagerOrder/{orderNumber}")
    public String getManagerOrder(Model model, @PathVariable Integer orderNumber, Principal principal){
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        Integer itemId = order.getItem().getId();
        model.addAttribute("order", orderService.getOrderByOrderNumber(orderNumber));
        model.addAttribute("item", itemService.getItemById(itemId));
        return success("managerOrder",model,principal);
    }
    @GetMapping("/manager/order/approve/{orderNumber}")
    public String approveOrder(@PathVariable int orderNumber, Model model){

        Order order = orderService.getOrderByOrderNumber(orderNumber);
        Integer itemId = order.getItem().getId();
        if (order.getStatus() != APPROVED){
        OrderDto orderDto = new OrderDto();
        orderDto.setSubmittedDate(order.getSubmittedDate());
        orderDto.setStatus(APPROVED);
        orderDto.setItemName(order.getItem().getName());
        orderDto.setItemQuantity(order.getItemQuantity());
        orderDto.setDeadlineDate(order.getDeadlineDate());
        orderDto.setComment(order.getComment());

        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("orderDto", orderDto);
        orderService.removeOrderByOrderNumber(orderNumber);

        return "approveOrder";
        }else
            return "approveOrderError";
    }
    @PostMapping("/manager/order/approve")
    public String postApproveOrder(@ModelAttribute("orderDto") OrderDto orderDto){
        Order order = new Order();
        //Integer itemId = order.getItem().getId();

        order.setOrderNumber(orderDto.getOrderNumber());
        order.setSubmittedDate(orderDto.getSubmittedDate());
        order.setItem(itemService.getItemById(orderDto.getItemId()));
        order.setItemQuantity(orderDto.getItemQuantity());
        order.setStatus(APPROVED);
        order.setDeadlineDate(orderDto.getDeadlineDate());
        order.setComment(orderDto.getComment());

        orderService.addOrder(order);

        Integer quantity = itemService.getItemById(orderDto.getItemId()).getQuantity();
        Integer itemQuantity = orderDto.getItemQuantity();
        Integer newQuantity = quantity - itemQuantity;
        itemService.getItemById(orderDto.getItemId()).setQuantity(newQuantity);

        return "redirect:/manager/orders";
    }

    @GetMapping("/manager/order/decline/{orderNumber}")
    public String declineOrder(@PathVariable int orderNumber, Model model){

        Order order = orderService.getOrderByOrderNumber(orderNumber);
        Integer itemId = order.getItem().getId();


        if (order.getItemQuantity() > itemService.getItemById(itemId).getQuantity()){
            OrderDto orderDto = new OrderDto();
            orderDto.setSubmittedDate(order.getSubmittedDate());
            orderDto.setStatus(DECLINED);
            orderDto.setItemName(order.getItem().getName());
            orderDto.setItemQuantity(order.getItemQuantity());
            orderDto.setDeadlineDate(order.getDeadlineDate());
            orderDto.setComment(order.getComment());

            model.addAttribute("items", itemService.getAllItems());
            model.addAttribute("orderDto", orderDto);
            orderService.removeOrderByOrderNumber(orderNumber);

            return "declineOrder";
        }else
            return "declineOrderError";
    }
    @PostMapping("/manager/order/decline")
    public String postDeclineOrder(@ModelAttribute("orderDto") OrderDto orderDto){
        Order order = new Order();

        order.setOrderNumber(orderDto.getOrderNumber());
        order.setSubmittedDate(orderDto.getSubmittedDate());
        order.setItem(itemService.getItemById(orderDto.getItemId()));
        order.setItemQuantity(orderDto.getItemQuantity());
        order.setStatus(DECLINED);
        order.setDeadlineDate(orderDto.getDeadlineDate());
        order.setComment(orderDto.getComment());

        orderService.addOrder(order);

        return "redirect:/manager/orders";
    }

}
