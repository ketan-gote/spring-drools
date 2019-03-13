package com.drools.repo;

import com.drools.entity.DroolsDRL;

public interface DroolsDRLRepository {

	public DroolsDRL findById(String id) throws Exception;
	
	public void save(DroolsDRL droolsDrl) throws Exception;
	
}
