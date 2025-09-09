package com.concurrency.concurrency_stock_order;

import com.concurrency.concurrency_stock_order.domain.Product;
import com.concurrency.concurrency_stock_order.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseConnectionTest {

    @Autowired
    private ProductRepository productStockRepository;

    @Test
    void DB연결_및_조회_테스트() {
        Product stock = productStockRepository.findById(1L).orElseThrow();
        assertThat(stock.getName()).isEqualTo("상품A");
        assertThat(stock.getStock()).isEqualTo(100);
    }
}
