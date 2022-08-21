package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Category;
import com.app.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product>{

	@Override
	List<Product> findAll();
	
	Optional<Product> findByNameEn(String nameEn);
	
	Optional<Product> findByNameVi(String nameVi);
	
	Optional<Product> findByCode(String code);
	
	List<Product> findByCategory(Category category);
}
