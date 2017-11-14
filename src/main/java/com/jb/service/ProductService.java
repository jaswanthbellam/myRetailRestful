package com.jb.service;

import com.jb.model.Product;

public interface ProductService {
	
	public Product getProductById(long id);
	public Product updateProduct(long id, Product product);

}
