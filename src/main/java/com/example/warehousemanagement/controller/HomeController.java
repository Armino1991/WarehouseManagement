package com.example.warehousemanagement.controller;

import com.example.warehousemanagement.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{

    @GetMapping({"/","/home"})
    public String getHomeView(Model model, Principal principal){
        String role = extractRole(principal);
        if (Role.SYSTEM_ADMIN.equals(role)){
            return success("adminHome", model, principal);
        }
        else if (Role.CLIENT.equals(role)){
            return success("clientHome", model, principal);
        }
        else if (Role.WAREHOUSE_MANAGER.equals(role)){
            return success("managerHome", model, principal);
        }
        return success("index", model, principal);
    }

}
