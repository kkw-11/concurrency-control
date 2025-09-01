package com.concurrency.concurrency_stock_order.repository;

import com.concurrency.concurrency_stock_order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
