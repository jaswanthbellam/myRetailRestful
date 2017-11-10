package com.jb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jb.model.Product;
import com.jb.service.ProductServiceImpl;

@RestController
@RequestMapping("/myRetailRestful/api")
public class ProductsController {

	@Autowired
	ProductServiceImpl productService;

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduct(@PathVariable("id") long id) {

		Product product = productService.getProductById(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

}