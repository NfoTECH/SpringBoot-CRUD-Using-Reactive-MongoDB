package com.nfotech.springreactivemongocrud.repository;

import com.nfotech.springreactivemongocrud.dto.ProductDto;
import com.nfotech.springreactivemongocrud.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

//    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);
    Flux<ProductDto> findByPriceBetween(Mono<Double> price, Mono<Double> price2);
}
