package com.feriodev.spring.cloud.app.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	private String id;
	
	@NotEmpty
	private String name;
	
	@NotNull
	private Double price;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
	
	@Transient
	private Integer port;

	public Product(@NotEmpty String name, @NotNull Double price) {
		super();
		this.name = name;
		this.price = price;
	}
	
}
