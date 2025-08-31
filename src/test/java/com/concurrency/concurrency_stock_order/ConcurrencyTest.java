package com.concurrency.concurrency_stock_order;

import com.concurrency.concurrency_stock_order.domain.ProductStock;
import com.concurrency.concurrency_stock_order.exception.OutOfStockException;
import com.concurrency.concurrency_stock_order.repository.OrderRepository;
import com.concurrency.concurrency_stock_order.repository.ProductStockRepository;
import com.concurrency.concurrency_stock_order.service.OrderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired private OrderService orderService;
    @Autowired private ProductStockRepository productStockRepository;
    @Autowired private OrderRepository orderRepository;

    private Long productId;
    private final Long userId = 1L;

    @BeforeEach
    void init() {
        orderRepository.deleteAll();
        productStockRepository.deleteAll();

        ProductStock stock = new ProductStock("상품A", 100);
        productStockRepository.save(stock);
        productId = stock.getId();
    }

    private int 주문_동시요청(int 요청개수) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(요청개수);
        AtomicInteger exceptionCount = new AtomicInteger(0);

        for (int i = 0; i < 요청개수; i++) {
            executor.submit(() -> {
                try {
                    orderService.placeOrder(userId, productId, 1);
                } catch (OutOfStockException e) {
                    exceptionCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println("예상 외 예외: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        return exceptionCount.get();
    }

    @Test
    void 정상_주문_테스트() throws InterruptedException {
        int initialStock = 100;
        int exceptionCount = 주문_동시요청(initialStock);

        int orderCount = orderRepository.findAll().size();
        int remainStock = productStockRepository.findById(productId).orElseThrow().getStock();

        System.out.println("정상 테스트 - 주문 수: " + orderCount);
        System.out.println("정상 테스트 - 남은 재고: " + remainStock);
        System.out.println("정상 테스트 - 예외 수: " + exceptionCount);

        assertThat(orderCount).isEqualTo(initialStock);
        assertThat(remainStock).isEqualTo(0);
        assertThat(exceptionCount).isEqualTo(0);
    }

    @Test
    void 재고_초과_비정상_주문_테스트() throws InterruptedException {
        int requestCount = 110;
        int exceptionCount = 주문_동시요청(requestCount);

        int orderCount = orderRepository.findAll().size();
        int remainStock = productStockRepository.findById(productId).orElseThrow().getStock();

        System.out.println("비정상 테스트 - 주문 수: " + orderCount);
        System.out.println("비정상 테스트 - 남은 재고: " + remainStock);
        System.out.println("비정상 테스트 - 예외 수: " + exceptionCount);

        assertThat(exceptionCount).isEqualTo(10); // 110개 요청 중 10개는 재고 부족으로 예외
        assertThat(orderCount).isEqualTo(100); // 정확하게 100개만 성공해야 한다
        assertThat(remainStock).isEqualTo(0);
    }
}

