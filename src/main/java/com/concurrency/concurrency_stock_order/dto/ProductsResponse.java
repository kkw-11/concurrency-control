package com.concurrency.concurrency_stock_order.dto;

import com.concurrency.concurrency_stock_order.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ProductsResponse {

    private List<ProductDto> products;

    // 정적 팩토리 메서드
    public static ProductsResponse of(List<Product> productList) {
        List<ProductDto> dtoList = productList.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
        return new ProductsResponse(dtoList);
    }

    @Getter
    @AllArgsConstructor
    public static class ProductDto {
        private Long id;
        private String name;
        private Integer price;
        private Integer stock;

        public static ProductDto from(Product product) {
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            );
        }
    }
}
