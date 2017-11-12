package com.jb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

	private long id;
	private String name;
	@JsonProperty("current_price")
	private Price currentPrice;

	public Product() {

	}

	public Product(long id, String name, Price currentPrice) {
		this.id = id;
		this.name = name;
		this.currentPrice = currentPrice;

	}

	public Price getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Price currentPrice) {
		this.currentPrice = currentPrice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
