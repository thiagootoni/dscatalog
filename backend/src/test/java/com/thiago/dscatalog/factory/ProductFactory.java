package com.thiago.dscatalog.factory;

import java.time.Instant;

import com.thiago.dscatalog.dto.ProductDTO;
import com.thiago.dscatalog.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "GoodPhone", 1000.0, "img url", Instant.parse("2020-12-06T03:00:00Z"));
		product.getCategories().add(CategoryFactory.createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static ProductDTO createProductDTO(Long id) {
		ProductDTO productDto = createProductDTO();
		productDto.setId(id);
		return productDto;
	}

}
