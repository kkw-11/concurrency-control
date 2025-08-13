package com.concurrency.concurrency_stock_order.repository;

import com.concurrency.concurrency_stock_order.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
