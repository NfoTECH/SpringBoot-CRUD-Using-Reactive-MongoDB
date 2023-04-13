package com.nfotech.springreactivemongocrud.controller;

import com.nfotech.springreactivemongocrud.dto.ProductDto;
import com.nfotech.springreactivemongocrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<ProductDto> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

//    @GetMapping("/product-range")
//    public Flux<ProductDto> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
//        return productService.getProductInRange(min, max);
//    }

    @GetMapping("/product-range/{min}/{max}")
    public Flux<ProductDto> getProductInRange(@PathVariable double min, @PathVariable double max) {
        return productService.getProductInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> addProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return productService.addProduct(productDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String id) {
        return productService.updateProduct(productDtoMono, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}