package com.jb.dao;

import com.jb.exception.NotFoundException;
import com.jb.model.Price;

public interface ProductDao {
	
	public String getProductName(long Id) throws NotFoundException;
	public String getProductPrice(long Id) throws NotFoundException;
	public int updateProductPrice(long id, Price price) throws NotFoundException;

}
