package com.thiago.dscatalog.factory;

import com.thiago.dscatalog.dto.CategoryDTO;
import com.thiago.dscatalog.entities.Category;

public class CategoryFactory {
	
	public static Category createCategory() {
		return new Category(3L, "Computadores");
	}
	
	public static CategoryDTO createCategoryDto() {
		return new CategoryDTO(createCategory());
	}

}
