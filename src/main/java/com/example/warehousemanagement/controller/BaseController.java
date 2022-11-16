package com.example.warehousemanagement.controller;

import com.example.warehousemanagement.model.Role;
import com.example.warehousemanagement.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Objects;

@Controller
public class BaseController {

    @Autowired
    private MenuService menuService;

    public String success(String viewName, Model model, Principal principal){

        String username = extractUsername(principal);
        String role = extractRole(principal);

        model.addAttribute("username", username);
        model.addAttribute("menu_list", menuService.getMenuListByRole(role));

        return viewName;
    }

    protected String extractUsername(Principal principal) {
        if (Objects.isNull(principal)) {
            return "";
        }

        // Cast principal to UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) principal;

        return usernamePasswordAuthenticationToken.getName();
    }

    protected String extractRole(Principal principal) {
        if (Objects.isNull(principal)) {
            return Role.ANONYMOUS;
        }

        // Cast principal to UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) principal;

        // Cast authority to GrantedAuthority
        GrantedAuthority grantedAuthority = (GrantedAuthority)
                usernamePasswordAuthenticationToken.getAuthorities().toArray()[0];

        // Return role
        return grantedAuthority.getAuthority();
    }

}
