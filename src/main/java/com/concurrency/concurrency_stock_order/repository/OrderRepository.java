package com.concurrency.concurrency_stock_order.repository;

import com.concurrency.concurrency_stock_order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
