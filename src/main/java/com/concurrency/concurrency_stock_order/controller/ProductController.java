package com.concurrency.concurrency_stock_order.controller;

import com.concurrency.concurrency_stock_order.dto.ProductsResponse;
import com.concurrency.concurrency_stock_order.dto.common.ApiResponse;
import com.concurrency.concurrency_stock_order.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products-offset")
    public ApiResponse<ProductsResponse> getProductsByOffset(
            @PageableDefault(size = 100) Pageable pageable) {
        ProductsResponse response = productService.getProductsByOffset(pageable);
        return ApiResponse.success(response);
    }

    @GetMapping("/products-cursor")
    public ApiResponse<ProductsResponse> getProductsByCursor(
            @RequestParam(name = "startId") Long startId,
            @RequestParam(name = "pageSize") int pageSize) {
        ProductsResponse response = productService.getProductsByCursor(startId, pageSize);
        return ApiResponse.success(response);
    }
}
