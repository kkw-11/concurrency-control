package com.concurrency.concurrency_stock_order.service;

import com.concurrency.concurrency_stock_order.domain.Product;
import com.concurrency.concurrency_stock_order.dto.ProductsResponse;
import com.concurrency.concurrency_stock_order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductsResponse getProductsByOffset(Pageable pageable) {
        List<Product> productPage = productRepository.findAll(pageable).getContent();
        return ProductsResponse.of(productPage);
    }

    public ProductsResponse getProductsByCursor(Long startId, int pageSize) {
        List<Product> productPage = productRepository.findAllByCursor(startId, pageSize);
        return ProductsResponse.of(productPage);
    }
}
