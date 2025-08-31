package com.concurrency.concurrency_stock_order.dto;

import com.concurrency.concurrency_stock_order.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    private Integer stock; // 재고 수량

    public Product(String name, Integer price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 비즈니스 로직
    public void decreaseStock(int quantity) throws OutOfStockException {
        if (this.stock < quantity) {
            throw new OutOfStockException();
        }
        this.stock -= quantity;
    }
}
