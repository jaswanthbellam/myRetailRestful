package com.jb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.dao.ProductDaoImpl;
import com.jb.exception.NotFoundException;
import com.jb.model.Price;
import com.jb.model.Product;
import com.jb.util.JsonConverter;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDaoImpl productDao;
	@Autowired
	private ObjectMapper objectMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
		}catch (NotFoundException e) {
			return new Product(404);
		}
		catch (Exception e) {
			logger.error( "failed!", e );
		}
		return null;

	}
	
	public Product updateProduct(long id, Product product) {
		
		try {
			int response = productDao.updateProductPrice(id,product.getCurrentPrice());
			if(response == 200)
				return product;
		} catch (NotFoundException e) {
			product.setStatus(404);
			return product;
		}
		return null;
	}

}
