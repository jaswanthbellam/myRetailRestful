package com.jb.myRetailRestfulTest

import com.jb.dao.ProductDaoImpl
import com.jb.model.Price
import com.jb.model.Product
import com.jb.service.ProductServiceImpl
import spock.lang.Specification

class ProductServiceImpSpec extends Specification{

	
	def productDaoImplMock = Mock(ProductDaoImpl)
	
	def 'test product server by id' () {
		
		given:
		String price = '''{\"current_price\" : {\"value\":13.26,\"currency_code\":"USD}}'''
		String response = '''{\"product\" : {\"item\": {\"product_description\" : {\"title\" : \"The Bluesky\"}}}}'''
		productDaoImplMock.getProductName >>  response 
		productDaoImplMock.getProductPrice >>  price  
		def productService = new ProductServiceImpl(productDao : productDaoImplMock)
		
		when:
		def result = productService.getProductById(13068)
		
		
		then:
		assert result instanceof Product
		
		
	}
}
