package com.thiago.dscatalog.factory;

import java.time.Instant;

import com.thiago.dscatalog.dto.ProductDTO;
import com.thiago.dscatalog.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		return new Product(1L, "Phone", "GoodPhone", 1000.0, "img url", Instant.parse("2021-12-06T03:00:00Z"));
	}
	
	public static ProductDTO createProductDTO() {
		return new ProductDTO(createProduct());
	}

}
