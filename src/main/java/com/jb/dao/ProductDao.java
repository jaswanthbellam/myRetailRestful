package com.jb.dao;

import com.jb.exception.NotFoundException;

public interface ProductDao {
	
	public String getProductName(long Id) throws NotFoundException;
	public String getProductPrice(long Id) throws NotFoundException;

}
