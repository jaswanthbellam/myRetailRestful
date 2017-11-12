package com.jb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {

	private double value;
	@JsonProperty("currency_code")
	private String currencyCode; // TODO Can we make it enum?

	public Price() {

	}

	public Price(double value, String currencyCode) {
		this.value = value;
		this.currencyCode = currencyCode;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
