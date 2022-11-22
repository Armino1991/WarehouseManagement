package com.example.warehousemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "SUBMITTED_DATE")
    private Date submittedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusType status;

    @Column(name = "ITEM_QUANTITY")
    private int itemQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    private Item item;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DEADLINE_DATE")
    private Date deadlineDate;

    @Column(name="COMMENT")
    private String comment;

}
