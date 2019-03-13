package com.drools.service;

import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drools.entity.DroolsDRL;
import com.drools.repo.DroolsDRLRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DiscountServiceImpl implements DiscountService
{
	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private DroolsDRLRepository droolsDRLRepository;
	 
	@Autowired
	private DroolsServiceImpl droolsSessionHandler;
	
	public void applyDiscount() throws Exception {
		DroolsDRL droolsDrl = droolsDRLRepository.findById("6c2e77ea-5bde-4200-903b-606fc40b74b3");
		
		String content = droolsDrl.getDsl();
		
		KieSession kieSession = droolsSessionHandler.getKSession(content);
		
		FactType factType = kieSession.getKieBase().getFactType("com.drools.examples", "CustomerCart");
		Object runtimeInstance = factType.newInstance();
			
		Object newobj = this.getObject(" {\"id\":\"ID-1234567890\",\"customerId\":\"CustomerId-1234567890\",\"customerType\":\"GOLD\",\"totalAmount\":2000.0,\"discount\":0,\"payableAmount\":2000.0}", runtimeInstance);
		runtimeInstance = newobj;
		kieSession.insert(runtimeInstance);
		kieSession.fireAllRules();
		kieSession.dispose();
				
		System.out.println("Total amount : "+factType.get( runtimeInstance, "totalAmount" ));
		System.out.println("Discount Applied : "+factType.get( runtimeInstance, "discount" ));
		System.out.println("Total amount after discount : "+factType.get( runtimeInstance, "payableAmount" ));
	}
	
	public void applyDiscount1() throws Exception {
	
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
	
		FactType factType = kieSession.getKieBase().getFactType("com.drools.examples", "CustomerCart");
		Object runtimeInstance = factType.newInstance();
		
//		System.out.println( "RUNTIME INSTANCE "+runtimeInstance.getClass());
//		factType.set(runtimeInstance,"id","ID-1234567890");
//		factType.set(runtimeInstance,"customerId","CustomerId-1234567890");
//		factType.set(runtimeInstance,"customerType","GOLD");
//		factType.set(runtimeInstance,"totalAmount",new Double("2000"));
//		factType.set(runtimeInstance,"discount",0);
//		factType.set(runtimeInstance,"payableAmount",new Double("2000"));
//		
//		String json = this.writeValue(runtimeInstance);
//		
		Object newobj = this.getObject(" {\"id\":\"ID-1234567890\",\"customerId\":\"CustomerId-1234567890\",\"customerType\":\"GOLD\",\"totalAmount\":2000.0,\"discount\":0,\"payableAmount\":2000.0}", runtimeInstance);
		runtimeInstance = newobj;
		kieSession.insert(runtimeInstance);
		kieSession.fireAllRules();
		kieSession.dispose();
				
		System.out.println("Total amount : "+factType.get( runtimeInstance, "totalAmount" ));
		System.out.println("Discount Applied : "+factType.get( runtimeInstance, "discount" ));
		System.out.println("Total amount after discount : "+factType.get( runtimeInstance, "payableAmount" ));
		
		

	}
	
	private String writeValue(Object obj){
		 try {
			String str = new ObjectMapper().writeValueAsString(obj);
			System.out.println("Printing JSON \n "+str);
			return str;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Object getObject(String json, Object obj){
		
		try {
			Object objnew = new ObjectMapper().readValue(json, obj.getClass());
			return objnew;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		

	}

}
