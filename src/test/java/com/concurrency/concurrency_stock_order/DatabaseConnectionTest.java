package com.concurrency.concurrency_stock_order;

import com.concurrency.concurrency_stock_order.domain.ProductStock;
import com.concurrency.concurrency_stock_order.repository.ProductStockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private ProductStockRepository productStockRepository;

    @Test
    void DB연결_및_조회_테스트() {
        ProductStock stock = productStockRepository.findById(1L).orElseThrow();
        assertThat(stock.getName()).isEqualTo("상품A");
        assertThat(stock.getStock()).isEqualTo(100);
    }
}
