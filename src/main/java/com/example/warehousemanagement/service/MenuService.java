package com.example.warehousemanagement.service;

import com.example.warehousemanagement.model.Menu;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class MenuService {

    private static final String ANONYMOUS = "ANONYMOUS";
    private static final String SYSTEM_ADMIN = "SYSTEM_ADMIN";

    private static final String CLIENT = "CLIENT";

    private static final String WAREHOUSE_MANAGER = "WAREHOUSE_MANAGER";

    private static final List<Menu> ANONYMOUS_MENU = Arrays.asList(
            new Menu("Homepage", "/"),
            new Menu("Logout", "/logout")
    );

    private static final List<Menu> SYSTEM_ADMIN_MENU = Arrays.asList(
            new Menu("Manage Users", "/admin/users"),
            new Menu("Logout", "/logout")
    );


    private static final List<Menu> CLIENT_MENU = Arrays.asList(
            new Menu("Items", "/items"),
            new Menu("Logout", "/logout")
    );

    private static final List<Menu> WAREHOUSE_MANAGER_MENU = Arrays.asList(
            new Menu("Manage Orders", "/manager/orders"),
            new Menu("Manage Items", "/manager/items"),
            new Menu("Manage Trucks", "/manager/trucks"),
            new Menu("Logout", "/logout")
    );

    public List<Menu> getMenuListByRole(String role){

        if (Objects.isNull(role)) {
            return ANONYMOUS_MENU;
        }

        switch (role){
            case SYSTEM_ADMIN:
                return SYSTEM_ADMIN_MENU;
            case CLIENT:
                return CLIENT_MENU;
            case WAREHOUSE_MANAGER:
                return WAREHOUSE_MANAGER_MENU;
            default:
                return ANONYMOUS_MENU;
        }
    }
}
