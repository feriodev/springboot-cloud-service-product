package com.feriodev.spring.cloud.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.feriodev.spring.cloud.app.model.Product;
import com.feriodev.spring.cloud.app.services.ProductService;

import reactor.core.publisher.Flux;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringbootCloudServiceProductApplication implements CommandLineRunner{

private static final Logger log = LoggerFactory.getLogger(SpringbootCloudServiceProductApplication.class);
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("products").subscribe();		
				
		Flux.just(new Product("Computadora", 1000.00),
					new Product("Monitor", 235.00),
					new Product("Mouse", 40.00),
					new Product("Estabilizador", 60.00),
					new Product("Teclado", 50.00),
					new Product("Camara Web", 150.00))
			.flatMap(p -> {
				p.setCreateAt(new Date());
				return service.save(p);
			}).subscribe(p -> log.info("Insert product " + p.getId()));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootCloudServiceProductApplication.class, args);
	}

}
