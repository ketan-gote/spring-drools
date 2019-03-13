package com.drools.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drools.service.DroolsRuntimeService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class DemoApi {
	
	@Autowired
	private DroolsRuntimeService droolsRuntimeService;
	
	
	@GetMapping("/execute")
	public ResponseEntity<Object> executeRule() throws Exception{
		System.out.println("*******IN EXECUTE RULE************");
		Object newobj = droolsRuntimeService.runRule("6c2e77ea-5bde-4200-903b-606fc40b74b3"," {\"id\":\"ID-1234567890\",\"customerId\":\"CustomerId-1234567890\",\"customerType\":\"GOLD\",\"totalAmount\":2000.0,\"discount\":0,\"payableAmount\":2000.0}");
		System.out.println(newobj);
		return new ResponseEntity<Object>(newobj, HttpStatus.OK);
	}

}
