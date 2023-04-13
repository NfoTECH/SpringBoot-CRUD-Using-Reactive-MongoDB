package com.nfotech.springreactivemongocrud.utils;

import com.nfotech.springreactivemongocrud.dto.ProductDto;
import com.nfotech.springreactivemongocrud.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    // Convert Product to ProductDto
    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto); // (source, target)
        return productDto;
    }

    // Convert ProductDto to Product
    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product); // (source, target)
        return product;
    }
}
