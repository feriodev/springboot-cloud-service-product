package com.feriodev.spring.cloud.app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.feriodev.spring.cloud.app.model.Product;
import com.feriodev.spring.cloud.app.repository.ProductRepository;
import com.feriodev.spring.cloud.app.services.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private Environment environment;
	
	@Override
	public Mono<Product> save(Product product) {
		return repository.save(product);
	}

	@Override
	public Flux<Product> findAll() {
		return repository.findAll().map(p -> {
			Integer port = Integer.parseInt(environment.getProperty("local.server.port"));
			p.setPort(port);
			return p;
		});
	}

	@Override
	public Mono<Product> findById(String id) {
		return repository.findById(id).map(p -> {
				Integer port = Integer.parseInt(environment.getProperty("local.server.port"));
				p.setPort(port);
				return p;
			});
}

	@Override
	public Mono<Void> delete(Product product) {
		return repository.delete(product);
	}

}
