package com.concurrency.concurrency_stock_order.repository;

import com.concurrency.concurrency_stock_order.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}
