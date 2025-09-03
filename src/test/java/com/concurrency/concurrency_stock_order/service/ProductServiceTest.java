package com.concurrency.concurrency_stock_order.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
class ProductServiceTest {
    private final static int ITERATION_COUNT = 100;

    private final static int PAGE_SIZE = 100;
    private final static int PAGE_NUMBER = 2999;
    private final static int START_ID = 299901;

    @Autowired
    private ProductService productService;

    @DisplayName("상품 목록 offset 기반 조회 성능 테스트")
    @Test
    void getProductsByOffset() {
        // given
        PageRequest pageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);

        // when
        long totalTime = 0;
        for (int i = 0; i < ITERATION_COUNT; i++) {
            long startTime = System.currentTimeMillis();
            System.out.println("시작시간"+startTime);
            productService.getProductsByOffset(pageRequest);
            long endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
        }

        // then
        log.info("---- Offset 기반 성능 평가 ----");
        log.info("Total time: " + totalTime + " ms");
        log.info("Average time: " + totalTime / ITERATION_COUNT + " ms");
    }

    @DisplayName("상품 목록 cursor 기반 조회 성능 테스트")
    @Test
    void getProductsByCursor() {
        // when
        long totalTime = 0;
        for (int i = 0; i < ITERATION_COUNT; i++) {
            long startTime = System.currentTimeMillis();
            productService.getProductsByCursor((long) START_ID, PAGE_SIZE);
            long endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
        }

        // then
        log.info("---- Cursor 기반 성능 평가 ----");
        log.info("Total time: " + totalTime + " ms");
        log.info("Average time: " + totalTime / ITERATION_COUNT + " ms");
    }

}