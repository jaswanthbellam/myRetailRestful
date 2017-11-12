package com.jb.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.dao.ProductDaoImpl;
import com.jb.model.Price;
import com.jb.model.Product;
import com.jb.util.JsonConverter;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDaoImpl productDao;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Product getProductById(long id) {

		try {
			// Get name from api
			String response = productDao.getProductName(id);
			String name = JsonConverter.convertToJsonObject(response).get("product").getAsJsonObject().get("item")
					.getAsJsonObject().get("product_description").getAsJsonObject().get("title").getAsString();

			// Get price from mongodb
			String currentPrice = productDao.getProductPrice(id);
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			Price priceObj = objectMapper.readValue(currentPrice, Price.class);
			
			Product product = new Product(id, name, priceObj);
			return product;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
