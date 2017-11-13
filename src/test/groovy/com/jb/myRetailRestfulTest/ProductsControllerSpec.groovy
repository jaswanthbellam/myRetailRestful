package com.jb.myRetailRestfulTest

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

import com.jb.controller.ProductsController
import com.jb.model.Price
import com.jb.model.Product
import com.jb.service.ProductServiceImpl

import groovy.json.JsonSlurper
import spock.lang.Specification

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductsControllerSpec extends Specification {
	
	static OK = 200
	static NOT_FOUND = 404
	static VALID_ID = 130248
	

	def productServiceImplMock = Mock(ProductServiceImpl)
	def productsController = new ProductsController()
	MockMvc mockMvc = standaloneSetup(productsController).build()
	
	def setup() {
		productsController.productService = productServiceImplMock
	}
	
	def 'getProduct test hits the URL and parses JSON output' () {

		when: 'rest get product url is hit'
		def response = mockApi.perform(get('/product/130248')).andReturn().response
		def content = new JsonSlurper().parseText(reponse.contentAsString)

		then:
		
		def currentPrice = new Price(13.26,"USD")
		def product = new Product(VALID_ID,"The BuleSKy",currentPrice)
		
		1 * productService.getProductbyId(_) >> product
		
		response.status == OK.value()
		response.contentType.contains('application/json')
		response.contentType == 'application/json;charset=UTF-8'
		response.contentAsString == '''{ \"id\" : 130678, \"name\" : \"The Blue Sky\", \"current_price\" : {\"value\":13.26,\"currency_code\":"USD}}'''
		
		
	}
	
}
