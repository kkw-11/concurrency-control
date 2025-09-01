package com.concurrency.concurrency_stock_order;

import com.concurrency.concurrency_stock_order.domain.Product;
import com.concurrency.concurrency_stock_order.domain.User;
import com.concurrency.concurrency_stock_order.exception.OutOfStockException;
import com.concurrency.concurrency_stock_order.repository.OrderRepository;
import com.concurrency.concurrency_stock_order.repository.ProductRepository;
import com.concurrency.concurrency_stock_order.repository.UserRepository;
import com.concurrency.concurrency_stock_order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired private OrderService orderService;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;

    private Long productId;
    private Long userId;
    private static final Integer stockCount = 100;

    @BeforeEach
    void init() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();

        // 유저 생성
        User user = new User("테스트유저");
        userRepository.save(user);
        userId = user.getId();

        // 상품 생성 (재고 100, 가격 1000)
        Product product = new Product("상품A", 100, stockCount);
        productRepository.save(product);
        productId = product.getId();
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
                    exceptionCount.incrementAndGet(); // 재고 부족 예외 카운트
                } catch (Exception e) {
                    System.out.println("예상 외 예외: " + e.getMessage());
                    exceptionCount.incrementAndGet(); // 다른 예외도 카운트
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        return exceptionCount.get();
    }

    @Test
    void 정상_주문_테스트() throws InterruptedException {
        int initialStock = stockCount;
        int exceptionCount = 주문_동시요청(initialStock);

        int orderCount = orderRepository.findAll().size();
        int remainStock = productRepository.findById(productId).orElseThrow().getStock();

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
        int remainStock = productRepository.findById(productId).orElseThrow().getStock();

        System.out.println("비정상 테스트 - 주문 수: " + orderCount);
        System.out.println("비정상 테스트 - 남은 재고: " + remainStock);
        System.out.println("비정상 테스트 - 예외 수: " + exceptionCount);

        assertThat(exceptionCount).isEqualTo(requestCount - stockCount); // 110개 요청 중 10개는 실패해야 함
        assertThat(orderCount).isEqualTo(stockCount);    // 정확히 100개만 성공해야 함
        assertThat(remainStock).isEqualTo(0);     // 재고는 0이어야 함
    }
}
