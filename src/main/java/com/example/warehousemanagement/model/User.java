package com.example.warehousemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private int userId;

    @NotEmpty
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotEmpty
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

}
