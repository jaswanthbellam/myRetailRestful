package com.jb.myRetailRestfulTest

import com.google.gson.JsonObject
import com.jb.util.JsonConverter

import spock.lang.Specification

class JsonConverterSpec extends Specification {
	
	String json = '''{ \"id\" : 130678, \"name\" : \"The Blue Sky\"}'''
	
	def 'Parses string to Json Object' () {
		
		expect:
		assert JsonConverter.convertToJsonObject(json) instanceof JsonObject
		assert JsonConverter.convertToJsonObject("") == null
	}
	
	def 'If argument is null then throw Null Pointer Exception' () {
		
		when:
		JsonConverter.convertToJsonObject(null)
		
		then:
		thrown(NullPointerException)
	}

}
