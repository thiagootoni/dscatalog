package com.thiago.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thiago.dscatalog.entities.Category;
import com.thiago.dscatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT DISTINCT prod FROM Product prod INNER JOIN prod.categories cats WHERE "
			+ "(COALESCE(:categories) IS NULL OR cats IN :categories) AND "
			+ "(:name = '' OR LOWER(prod.name) LIKE LOWER(CONCAT('%', :name, '%')))")
	public Page<Product> findAllPaginated(String name, List<Category> categories, Pageable pageable);
	
	@Query("SELECT obj FROM Product JOIN FECTH obj.categories WHERE obj IN :products")
	List<Product> findProductsWithCategories(List<Product> products);

}

