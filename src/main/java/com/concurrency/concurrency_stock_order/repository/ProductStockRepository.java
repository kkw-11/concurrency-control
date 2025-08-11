package com.concurrency.concurrency_stock_order.repository;

import com.concurrency.concurrency_stock_order.domain.ProductStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductStock p WHERE p.id = :id")
    ProductStock findByIdForUpdate(@Param("id") Long id);
}
