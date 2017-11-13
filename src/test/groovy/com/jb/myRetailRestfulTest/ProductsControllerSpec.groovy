package com.jb.myRetailRestfulTest

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import com.jb.model.Price
import com.jb.model.Product
import com.jb.service.ProductServiceImpl

import spock.lang.Specification

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductsControllerSpec extends Specification {
	
		@Autowired
		TestRestTemplate restTemplate
		def productService = Mock(ProductServiceImpl) 
	
		def 'getProduct test hits the URL and parses JSON output' () {
			given:
			def currentPrice = new Price(13.26,"USD")
			def product = new Product(13028,"The BuleSKy",currentPrice)
			1 * productService.getProductById(_) >> product
	
			when:
			ResponseEntity<Product> entity = restTemplate.getForEntity('/product/13028',Product.class)
	
			then:
			entity.statusCode == HttpStatus.OK
		}
	
}
