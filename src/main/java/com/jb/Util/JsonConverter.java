package com.jb.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonConverter {
	
	public static JsonObject convertToJsonObject(String jsonStr) {
		
		JsonObject jObj = new Gson().fromJson(jsonStr, JsonObject.class); 
		return jObj;
		
	}

}
