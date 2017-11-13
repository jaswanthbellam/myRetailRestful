package com.jb.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonConverter {
	
	public static JsonObject convertToJsonObject(String jsonStr) {
		
		if(jsonStr == null)
			throw new NullPointerException();
		JsonObject jObj = new Gson().fromJson(jsonStr, JsonObject.class); 
		return jObj;
		
	}

}
