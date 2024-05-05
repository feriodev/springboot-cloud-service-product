package com.feriodev.spring.cloud.app.services;

import com.feriodev.spring.cloud.app.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

	Mono<Product> save(Product product);
	
	Flux<Product> findAll();
	
	Mono<Product> findById(String id);
	
	Mono<Void> delete(Product product);
}
