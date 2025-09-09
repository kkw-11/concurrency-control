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

    public ProductsResponse getProducts(Long cursor, int pageSize) {
        List<Product> products;

        if (cursor == null) {
            // 첫 페이지
            products = productRepository.findFirstPage(pageSize);
        } else {
            products = productRepository.findAllByCursor(cursor, pageSize);
        }

        Long nextCursor = products.isEmpty() ? null :
                products.get(products.size() - 1).getId();

        boolean hasNext = (products.size() == pageSize);

        return ProductsResponse.of(products, nextCursor, hasNext, pageSize);
    }
}
