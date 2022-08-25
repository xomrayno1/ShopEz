package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.model.Category;
import com.app.model.Product;
import com.app.repository.ProductRepository;
import com.app.response.specification.ProductSpecification;
import com.app.service.ProductService;
import com.app.utils.Constant;

@Service
public class ProductServiceImpl implements ProductService{
	
	private ProductRepository proRepo;
	
	@Autowired
	public ProductServiceImpl(ProductRepository proRepo) {
		this.proRepo = proRepo;
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return proRepo.findAll();
	}

	@Override
	public Product update(Product instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.ACTIVE.getValue());
		return proRepo.save(instance);
	}

	@Override
	public void delete(Product instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.IN_ACTIVE.getValue());
		proRepo.save(instance);
	}

	@Override
	public Product findById(long id) {
		// TODO Auto-generated method stub
		return proRepo.findById(id).orElse(null);
	}

	@Override
	public Product insert(Product instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.ACTIVE.getValue());
		return proRepo.save(instance);
	}

	@Override
	public Product findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> doFilterSearchPagingProduct(String searchKey, int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		return proRepo.findAll(new ProductSpecification(searchKey), PageRequest.of(pageNumber - 1, pageSize));
	}

	@Override
	public Product findByCode(String code) {
		// TODO Auto-generated method stub
		return proRepo.findByCode(code).orElse(null);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		// TODO Auto-generated method stub
		return proRepo.findByCategory(category);
	}

}
