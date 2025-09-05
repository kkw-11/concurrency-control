package com.concurrency.concurrency_stock_order.domain;

import com.concurrency.concurrency_stock_order.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    public Product(String name, Integer price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 새로운 생성자 추가
    public Product(String name, Integer stock) {
        this.name = name;
        this.stock = stock;
    }

    // 재고 감소
    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new OutOfStockException();
        }
        this.stock -= quantity;
    }
}
