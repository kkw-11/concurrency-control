package com.concurrency.concurrency_stock_order.service;

import com.concurrency.concurrency_stock_order.domain.Order;
import com.concurrency.concurrency_stock_order.domain.OrderItem;
import com.concurrency.concurrency_stock_order.domain.Product;
import com.concurrency.concurrency_stock_order.domain.User;
import com.concurrency.concurrency_stock_order.repository.OrderRepository;
import com.concurrency.concurrency_stock_order.repository.ProductRepository;
import com.concurrency.concurrency_stock_order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void placeOrder(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findByIdForUpdate(productId);

        product.decreaseStock(quantity); // 재고 차감

        // 주문 생성
        Order order = new Order(user);

        // 주문 상품(OrderItem) 생성
        OrderItem orderItem = new OrderItem(product, quantity);

        // 연관관계 설정
        order.addOrderItem(orderItem);

        // 저장 (cascade 때문에 Order 저장 시 OrderItem도 같이 저장됨)
        orderRepository.save(order);
    }
}
