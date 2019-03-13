package com.drools.repo;

import org.springframework.stereotype.Repository;

import com.drools.entity.DroolsDRL;

@Repository
public class DroolsDRLRepositoryImpl extends GenericRepository<DroolsDRL> implements DroolsDRLRepository{
 
	@Override
	public DroolsDRL findById(String id) throws Exception {
		
		return (DroolsDRL) super.findById(new DroolsDRL(id));
	}

	@Override
	public void save(DroolsDRL droolsDrl) throws Exception {
		
		this.persist(droolsDrl);
		
	}

	
}
