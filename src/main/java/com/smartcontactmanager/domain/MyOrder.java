package com.smartcontactmanager.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class MyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myOrderId;
    private String orderId;
    private String amount;
    private String receipt;
    private String status;
    @ManyToOne
    private User user;
    public String paymentId;
}