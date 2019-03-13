package com.drools.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drools.service.DroolsRuntimeService;

@RestController
@RequestMapping(value = "/api")
public class DemoApi {
	
	@Autowired
	private DroolsRuntimeService discountService;
	
	
	@GetMapping("/execute")
	public void executeRule() throws Exception{
		System.out.println("*******IN EXECUTE RULE************");
		Object newobj = discountService.runRule("6c2e77ea-5bde-4200-903b-606fc40b74b3"," {\"id\":\"ID-1234567890\",\"customerId\":\"CustomerId-1234567890\",\"customerType\":\"GOLD\",\"totalAmount\":2000.0,\"discount\":0,\"payableAmount\":2000.0}");
		System.out.println(newobj);
	}

}
