package com.nfotech.springreactivemongocrud;

import com.nfotech.springreactivemongocrud.controller.ProductController;
import com.nfotech.springreactivemongocrud.dto.ProductDto;
import com.nfotech.springreactivemongocrud.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringReactiveMongoCrudApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @Test
    public void addProductTest() {
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("101", "Laptop", 1,  1000.0));
        when(productService.addProduct(productDtoMono)).thenReturn(productDtoMono);

        webTestClient.post().uri("/products")
                .body(productDtoMono, ProductDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class);
    }

    @Test
    public void getProductsByIdTest() {
        Flux<ProductDto> productDtoFLux = Flux.just(new ProductDto("101", "Laptop", 1,  1000.0),
                new ProductDto("102", "Mobile", 2,  500.0));
        when(productService.getAllProducts()).thenReturn(productDtoFLux);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDto("101", "Laptop", 1,  1000.0))
                .expectNext(new ProductDto("102", "Mobile", 2,  500.0))
                .verifyComplete();
    }

    @Test
    public void getProductByIdTest () {
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("101", "Laptop", 1,  1000.0));
        when(productService.getProductById(any())).thenReturn(productDtoMono);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products/102")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(productDto -> productDto.getId().equals("101"))
                .verifyComplete();
    }

    @Test
    public void updateProductTest () {
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("101", "Laptop", 1,  1000.0));
        when(productService.updateProduct(productDtoMono, "101")).thenReturn(productDtoMono);

        webTestClient.put().uri("/products/update/101")
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void deleteProductTest () {
        given(productService.deleteProduct(any())).willReturn(Mono.empty());

        webTestClient.delete().uri("/products/delete/102")
                .exchange()
                .expectStatus().isOk();
    }
}
