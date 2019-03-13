package com.drools.repo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.drools.entity.Product;

@Repository
public class ProductRepositoryImpl implements ProductRepository{

	@Autowired
	private PersistenceManagerFactory pmf;

	/**
	 * THIS METHOD IS USED TO CREATE INSTACE OF PERSISTENCEMANAGERFACTORY
	 */
	public PersistenceManager pm() {
		return pmf.getPersistenceManager();
	}
	
	@Override
	public List<Product> findAll() throws Exception {
		PersistenceManager pm = pm();
		try {
			Query query = pm.newQuery(Product.class);
			List<Product> list = (List<Product>) query.execute();
			List<Product> products = (List<Product>) pm.detachCopyAll(list);
			return products;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			pm.close();
		}
	}
}
