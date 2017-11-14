package com.jb.myRetailRestfulTest

import com.jb.dao.ProductDaoImpl
import com.jb.model.Price
import com.jb.model.Product
import com.jb.service.ProductServiceImpl

import spock.lang.Specification

class ProductServiceImpSpec extends Specification{

	ProductDaoImpl productDao = Mock()
	def productService = new ProductServiceImpl()
	
	def setup() {
		productService.productDao = productDao	
	}
	
	def 'test get product service by id' () {
		
		given:
		
		String price = new File("src/test/resources/price.json").text
		String response = new File("src/test/resources/redSkyApiResponse.json").text
		productDao.getProductName(13068) >> response
		productDao.getProductPrice(13068) >> price
		
		when:
		def result = productService.getProductById(13068)
		
		
		then:
		assert result instanceof Product	
		
	}
	
	def 'test product service to update product ' () {
		
		given:
		Price price = new Price(13.24,"USD");
		Product product = new Product(13068,"The BlueSky",price);
		int status = 200
		productDao.updateProductPrice(13068,price) >> status
		
		when:
		def result = productService.updateProduct(13068,product)
		
		
		then:
		assert result instanceof Product
	}
}
