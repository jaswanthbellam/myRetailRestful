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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.exception.NotFoundException;
import com.jb.model.Price;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

@Repository
public class ProductDaoImpl implements ProductDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private MongoClient mongoClient = null;
	@Autowired
	private ObjectMapper objectMapper;

	public String getProductName(long id) throws NotFoundException {

		HttpURLConnection conn = null;

		try {
			String api = System.getenv("redSkyApi") + "/" + id;
			URL url = new URL(api);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			logger.debug("\nSending 'GET' request to URL : " + url);
			int responseCode = conn.getResponseCode();
			logger.debug("Response Code : " + responseCode);
			if (responseCode == 404) {
				throw new NotFoundException("404 Not found");
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			logger.debug("Response from api: " + response.toString());
			return response.toString();
		} catch (MalformedURLException e) {
			logger.error("failed!", e);
		} catch (IOException e) {
			logger.error("failed!", e);
		}
		return null;

	}

	public String getProductPrice(long id) throws NotFoundException {

		DBCollection col = getMongoDBCollection();
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		logger.debug("Sending query to MongoDB database");
		DBObject dbObj = col.findOne(query);
		if (dbObj == null) {
			mongoClient.close();
			throw new NotFoundException("Not found");
		}
		BasicDBObject productObj = (BasicDBObject) dbObj.get("current_price");
		logger.debug("Got the price of object: " + productObj.toString());
		close();
		return productObj.toJson();

	}

	public int updateProductPrice(long id, Price price) throws NotFoundException {

		try {
			DBCollection col = getMongoDBCollection();
			BasicDBObject query = new BasicDBObject();
			query.put("id", id);

			String priceInJson = objectMapper.writeValueAsString(price);

			BasicDBObject updateQuery = new BasicDBObject();
			updateQuery.append("$set", new BasicDBObject().append("current_price.value", price.getValue()));

			logger.debug("Sending query to MongoDB database");
			WriteResult result = col.update(query, updateQuery);
			if (!result.wasAcknowledged()) {
				close();
				throw new NotFoundException("Not found");
			}
			close();
			return 200;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public DBCollection getMongoDBCollection() {
		String database = System.getenv("database");
		ServerAddress serverAddress = new ServerAddress(System.getenv("server"),
				Integer.valueOf(System.getenv("port")));
		MongoCredential redskyAuth = MongoCredential.createCredential(System.getenv("user"), database,
				System.getenv("pwd").toCharArray());
		List<MongoCredential> auths = new ArrayList<MongoCredential>();
		auths.add(redskyAuth);
		mongoClient = new MongoClient(serverAddress, auths);

		DB db = mongoClient.getDB(database);
		DBCollection col = db.getCollection("products");
		return col;
	}

	public void close() {
		mongoClient.close();
	}

}
