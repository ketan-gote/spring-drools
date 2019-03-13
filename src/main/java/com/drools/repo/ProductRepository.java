package com.drools.repo;

import java.util.List;

import com.drools.entity.Product;

public interface ProductRepository {

	public List<Product> findAll() throws Exception;
	
}
