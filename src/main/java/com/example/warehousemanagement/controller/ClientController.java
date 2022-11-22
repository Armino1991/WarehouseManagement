package com.example.warehousemanagement.controller;

import com.example.warehousemanagement.dto.OrderDto;
import com.example.warehousemanagement.model.Item;
import com.example.warehousemanagement.model.Order;
import com.example.warehousemanagement.model.StatusType;
import com.example.warehousemanagement.service.ItemService;
import com.example.warehousemanagement.service.OrderService;
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
public class ClientController extends BaseController{

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @GetMapping("/client")
    public String clientHome(){

        return "clientHome";
    }

    @GetMapping("/client/orders")
    public String getOrders(Model model){
        model.addAttribute("orders", orderService.getAllOrders());
        return "viewOrders";
    }

    @GetMapping("/client/orders/add")
    public String getOrderAdd(Model model){
        model.addAttribute("orderDto", new OrderDto());
        model.addAttribute("items", itemService.getAllItems());
        return "ordersAdd";
    }

    @PostMapping("/client/orders/add")
    public String postOrderAdd(@ModelAttribute("orderDto") OrderDto orderDto){
        Order order = new Order();

        order.setOrderNumber(orderDto.getOrderNumber());
        order.setSubmittedDate(orderDto.getSubmittedDate());
        order.setItem(itemService.getItemById(orderDto.getItemId()));
        order.setItemQuantity(orderDto.getItemQuantity());
        order.setStatus(CREATED);
        order.setDeadlineDate(orderDto.getDeadlineDate());
        order.setComment(orderDto.getComment());

        orderService.addOrder(order);

        return "redirect:/client/orders";
    }

    @GetMapping("/client/order/{status}")
    public String viewOrderByStatus(Model model, @PathVariable StatusType status){
        model.addAttribute("orders", orderService.getAllOrdersByStatus(status));
        return "viewOrders";
    }

    @GetMapping("/client/vieworder/{orderNumber}")
    public String getOrder(Model model, @PathVariable Integer orderNumber, Principal principal){
        model.addAttribute("order", orderService.getOrderByOrderNumber(orderNumber));
        return success("order",model,principal);
    }

    @GetMapping("/client/order/update/{orderNumber}")
    public String updateOrder(@PathVariable int orderNumber, Model model){

        Order order = orderService.getOrderByOrderNumber(orderNumber);

        if (order.getStatus() == CREATED || order.getStatus() == DECLINED){
            OrderDto orderDto = new OrderDto();
            orderDto.setSubmittedDate(order.getSubmittedDate());
            orderDto.setItemId(order.getItem().getId());
            orderDto.setItemQuantity(order.getItemQuantity());
            orderDto.setDeadlineDate(order.getDeadlineDate());
            orderDto.setComment(order.getComment());

            model.addAttribute("items", itemService.getAllItems());
            model.addAttribute("orderDto", orderDto);
            orderService.removeOrderByOrderNumber(orderNumber);

            return "updateOrder";
        } else
            return "updateOrderError";
    }

    @GetMapping("/client/order/submit/{orderNumber}")
    public String submitOrder(@PathVariable int orderNumber, Model model){

        Order order = orderService.getOrderByOrderNumber(orderNumber);
        if (order.getStatus() == CREATED || order.getStatus() == DECLINED) {
            OrderDto orderDto = new OrderDto();
            orderDto.setSubmittedDate(order.getSubmittedDate());
            orderDto.setStatus(AWAITING_APPROVAL);
            orderDto.setItemId(order.getItem().getId());
            orderDto.setItemQuantity(order.getItemQuantity());
            orderDto.setDeadlineDate(order.getDeadlineDate());
            orderDto.setComment(order.getComment());

            model.addAttribute("items", itemService.getAllItems());
            model.addAttribute("orderDto", orderDto);
            orderService.removeOrderByOrderNumber(orderNumber);

            return "submitOrder";
        } else
            return "submitOrderError";
    }

    @PostMapping("/client/order/submit")
    public String postSubmitOrder(@ModelAttribute("orderDto") OrderDto orderDto){
        Order order = new Order();

        order.setOrderNumber(orderDto.getOrderNumber());
        order.setSubmittedDate(orderDto.getSubmittedDate());
        order.setItem(itemService.getItemById(orderDto.getItemId()));
        order.setItemQuantity(orderDto.getItemQuantity());
        order.setStatus(AWAITING_APPROVAL);
        order.setDeadlineDate(orderDto.getDeadlineDate());
        order.setComment(orderDto.getComment());

        orderService.addOrder(order);

        return "redirect:/client/orders";
    }

    @GetMapping("/client/order/cancel/{orderNumber}")
    public String updateOrder(@PathVariable int orderNumber){

        Order order = orderService.getOrderByOrderNumber(orderNumber);

        if (order.getStatus() != FULFILLED || order.getStatus() != UNDER_DELIVERY || order.getStatus() != CANCELED ){

            orderService.removeOrderByOrderNumber(orderNumber);

            return "orderCanceled";
        } else
            return "orderCanceledError";
    }
}
