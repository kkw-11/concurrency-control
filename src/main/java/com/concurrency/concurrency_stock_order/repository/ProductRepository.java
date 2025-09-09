package com.concurrency.concurrency_stock_order.repository;

import com.concurrency.concurrency_stock_order.domain.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findByIdForUpdate(@Param("id") Long id);

    @Query(value = "select * from product p where p.id >= :id limit :pageSize", nativeQuery = true)
    List<Product> findAllByCursor(@Param("id") Long id, @Param("pageSize") int pageSize);

    @Query(value = "select * from product p order by p.id asc limit :pageSize", nativeQuery = true)
    List<Product> findFirstPage(@Param("pageSize") int pageSize);
}
