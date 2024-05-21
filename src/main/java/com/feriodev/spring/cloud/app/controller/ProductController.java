package com.feriodev.spring.cloud.app.controller;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feriodev.spring.cloud.app.model.Product;
import com.feriodev.spring.cloud.app.services.ProductService;

import jakarta.validation.Valid;
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
	
	@PostMapping
	public Mono<ResponseEntity<Product>> save(@Valid @RequestBody Product product) {
		return Mono.just(product)
			.flatMap(producto -> {
				if (producto.getCreateAt() == null) {
					producto.setCreateAt(new Date());
				}				
				return service.save(producto).map(p -> {
					return ResponseEntity
							.created(URI.create("/api/product/".concat(p.getId())))
							.contentType(MediaType.APPLICATION_JSON)
							.body(p);
					});
		});
		
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Product>> update(@PathVariable String id, @RequestBody Product product) {
		
		return service.findById(id).flatMap(p -> {
			p.setName(product.getName());
			p.setPrice(product.getPrice());
			return service.save(p);
		}).map(p -> ResponseEntity
				.created(URI.create("/api/product/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
		.defaultIfEmpty(ResponseEntity.notFound().build());
	
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
		return service.findById(id).flatMap(p -> {
			return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
}
