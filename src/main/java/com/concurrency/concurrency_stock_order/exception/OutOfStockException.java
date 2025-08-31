package com.concurrency.concurrency_stock_order.exception;

public class OutOfStockException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "재고가 부족합니다.";

    public OutOfStockException() {
        super(DEFAULT_MESSAGE);
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
