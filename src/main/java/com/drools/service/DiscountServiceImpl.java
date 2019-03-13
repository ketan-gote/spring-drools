package com.drools.service;

import java.util.List;

import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drools.entity.Product;
import com.drools.repo.ProductRepository;

@Service
public class DiscountServiceImpl implements DiscountService
{
	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private ProductRepository productRepository;
	
//	@Autowired
//	public DiscountServiceImpl(KieContainer kieContainer) {
//		this.kieContainer = kieContainer;
//	}

	
	@Override
	public void applyDiscount() throws Exception {
	
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		
		FactType factType = kieSession.getKieBase().getFactType("com.drools.examples", "CustomerCart");
		Object runtimeInstance = factType.newInstance();
		factType.set(runtimeInstance,"id","ID-1234567890");
		factType.set(runtimeInstance,"customerId","CustomerId-1234567890");
		factType.set(runtimeInstance,"customerType","GOLD");
		factType.set(runtimeInstance,"totalAmount",new Double("2000"));
		factType.set(runtimeInstance,"discount",0);
		factType.set(runtimeInstance,"payableAmount",new Double("2000"));
		
		kieSession.insert(runtimeInstance);
		kieSession.fireAllRules();
		kieSession.dispose();
				
		System.out.println("Total amount : "+factType.get( runtimeInstance, "totalAmount" ));
		System.out.println("Discount Applied : "+factType.get( runtimeInstance, "discount" ));
		System.out.println("Total amount after discount : "+factType.get( runtimeInstance, "payableAmount" ));

	}

}
