package com.feriodev.spring.cloud.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.feriodev.spring.cloud.app.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
