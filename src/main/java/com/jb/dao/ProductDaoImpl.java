package com.jb.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String getProductName(long id) {

		HttpURLConnection conn = null;
		String api = System.getenv("redSkyApi") + "/" + id;
		URL url;
		try {
			url = new URL(api);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			logger.debug("\nSending 'GET' request to URL : " + url);
			logger.debug("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			logger.debug("Resonse from api: "+response.toString());
			return response.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String getProductPrice(long id) {

		MongoClient mongoClient = null;
		try {
		String database = System.getenv("database");
		ServerAddress serverAddress = new ServerAddress(System.getenv("server"),
				Integer.valueOf(System.getenv("port")));
		MongoCredential redskyAuth = MongoCredential.createCredential(System.getenv("user"), database,
				System.getenv("pwd").toCharArray());
		List<MongoCredential> auths = new ArrayList<>();
		auths.add(redskyAuth);
		mongoClient = new MongoClient(serverAddress, auths);

		DB db = mongoClient.getDB(database);
		DBCollection col = db.getCollection("products");

		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		logger.debug("Sending query to MongoDB database");
		DBObject dbObj = col.findOne(query);
		BasicDBObject productObj = (BasicDBObject) dbObj.get("current_price");
		logger.debug("Got the price of object: "+ productObj.toString());
		return productObj.toJson();
		} catch(Exception e) {
			
		} finally {
			mongoClient.close();
		}
		return null;
	}

}
