package com.feriodev.spring.cloud.app.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feriodev.spring.cloud.app.model.Product;
import com.feriodev.spring.cloud.app.services.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping("/list")
	public Mono<ResponseEntity<Flux<Product>>> list(){
		return Mono.just(
				ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(service.findAll())
				);
	}
	
	@GetMapping("/detail/{id}")
	public Mono<ResponseEntity<Product>> detail(@PathVariable String id) throws InterruptedException{
		
		if (id.equals("10")) {
			throw new IllegalStateException("producto no encontrado");
		}
		
		if (id.equals("9")) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		return service.findById(id)
				.map(p -> ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}		
}
