package com.smartcontactmanager.repository;

import com.smartcontactmanager.domain.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyOrderRepository extends JpaRepository<MyOrder, Integer> {

    public MyOrder findByOrderId(String orderId);
}