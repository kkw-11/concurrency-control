package com.concurrency.concurrency_stock_order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders") // 'order'는 예약어라 테이블명 따로 설정
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductStock product;

    private Integer quantity;

    private LocalDateTime orderedAt;

    public Order(User user, ProductStock product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.orderedAt = LocalDateTime.now();
    }
}
