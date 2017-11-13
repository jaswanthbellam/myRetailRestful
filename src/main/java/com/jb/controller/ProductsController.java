package com.jb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jb.model.Product;
import com.jb.service.ProductServiceImpl;

@RestController
@RequestMapping("/myRetailRestful/api")
public class ProductsController {

	@Autowired
	public ProductServiceImpl productService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduct(@PathVariable("id") long id) {

		Product product = productService.getProductById(id);
		if (product != null) {
			if (product.getStatus() == 404) {
				return new ResponseEntity<String>("id not found", HttpStatus.NOT_FOUND);
			} else {
				logger.debug("Got the product data : " + product.toString());
				return new ResponseEntity<Product>(product, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		
		Product response = productService.updateProduct(id, product);
		if (response != null) {
			if (product.getStatus() == 404) {
				return new ResponseEntity<String>("id not found", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<String>("Data updated", HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
