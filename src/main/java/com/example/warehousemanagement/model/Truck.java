package com.example.warehousemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TRUCKS")
@AllArgsConstructor
@NoArgsConstructor
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRUCK_ID")
    private int truckId;

    @Column(name = "CH_NUMBER")
    private String chNumber;

    @Column(name = "L_PLATE")
    private String lPlate;
}
