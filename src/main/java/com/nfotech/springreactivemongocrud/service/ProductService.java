package com.nfotech.springreactivemongocrud.service;

import com.nfotech.springreactivemongocrud.dto.ProductDto;
import com.nfotech.springreactivemongocrud.repository.ProductRepository;
import com.nfotech.springreactivemongocrud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Get all products
    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll().map(AppUtils::entityToDto);
    }

    // Get product by id
    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id).map(AppUtils::entityToDto);
    }

//     Get product by price range

//    public Flux<ProductDto> getProductInRange(double min, double max) {
//        return productRepository.findByPriceBetween(Range.closed(min, max));

    public Flux<ProductDto> getProductInRange(double min, double max) {
        return productRepository.findByPriceBetween(Mono.just(min), Mono.just(max)); // Mono.just(min) is a Mono<Double>
    }

    // Add product

    public Mono<ProductDto> addProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono.map(AppUtils::dtoToEntity) // Convert productDtoMono to product
                .flatMap(productRepository::insert)      // Save product to DB
                .map(AppUtils::entityToDto);             // Convert product to productDto
    }

    // Update product
    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id) {
        return productRepository.findById(id)       // Get product from DB
                .flatMap(product -> productDtoMono.map(AppUtils::dtoToEntity))    // Convert productDtoMono to product
                .doOnNext(p -> p.setId(id))        // Keep the id of the product from DB
                .flatMap(productRepository::save)  // Save product to DB
                .map(AppUtils::entityToDto);      // Convert product to productDto

    }

    // Delete product
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
