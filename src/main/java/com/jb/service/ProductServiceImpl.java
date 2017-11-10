package com.jb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.Util.JsonConverter;
import com.jb.dao.ProductDaoImpl;
import com.jb.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDaoImpl productDao;

	@Override
	public Product getProductById(long id) {

		String response = productDao.getProductName(id);
		String name = JsonConverter.convertToJsonObject(response).get("product").getAsJsonObject().get("item")
				.getAsJsonObject().get("product_description").getAsJsonObject().get("title").getAsString();
		Product product = new Product(id,name,null);
		return product;
	}

}
