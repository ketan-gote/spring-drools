package com.drools.service;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.definition.type.FactType;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drools.entity.DroolsDRL;
import com.drools.repo.DroolsDRLRepository;
import com.drools.repo.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DiscountServiceImpl implements DiscountService
{
	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private DroolsDRLRepository droolsDRLRepository;
	 
	
	public void applyDiscount() throws Exception {
		DroolsDRL droolsDrl = droolsDRLRepository.findById("6c2e77ea-5bde-4200-903b-606fc40b74b3");
		System.out.println(droolsDrl);
		System.out.println(droolsDrl.getDsl());
		String drl = droolsDrl.getDsl();
		
		String content = drl;
		System.out.println("Read New Rules set from File");
		// load up the knowledge base
		KieServices ks = KieServices.Factory.get();
		String inMemoryDrlFileName = "src/main/resources/stateFulSessionRule.drl";
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write(inMemoryDrlFileName, ks.getResources().newReaderResource(new StringReader(content))
				.setResourceType(ResourceType.DRL));
		KieBuilder kieBuilder = ks.newKieBuilder(kfs).buildAll();
		if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
			System.out.println(kieBuilder.getResults().toString());
		}
		KieContainer kContainer = ks.newKieContainer(kieBuilder.getKieModule().getReleaseId());
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		KieBase kbase = kContainer.newKieBase(kbconf);
		KieSession kieSession = kieSession = kbase.newKieSession();
		
		
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
