package com.concurrency.concurrency_stock_order.service;

import com.concurrency.concurrency_stock_order.domain.Order;
import com.concurrency.concurrency_stock_order.domain.ProductStock;
import com.concurrency.concurrency_stock_order.domain.User;
import com.concurrency.concurrency_stock_order.repository.OrderRepository;
import com.concurrency.concurrency_stock_order.repository.ProductStockRepository;
import com.concurrency.concurrency_stock_order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductStockRepository productStockRepository;
    private final OrderRepository orderRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void placeOrder(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        // 락을 걸고 상품 조회
        ProductStock product = productStockRepository.findByIdForUpdate(productId);

        product.decreaseStock(quantity); // 재고 차감
        orderRepository.save(new Order(user, product, quantity));
    }
}
