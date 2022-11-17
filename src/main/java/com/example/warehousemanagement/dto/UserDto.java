package com.example.warehousemanagement.dto;

import lombok.Data;

@Data
public class UserDto {

    private int userId;
    private String username;
    private String password;
    private String role;
}
