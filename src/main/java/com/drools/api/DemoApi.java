package com.drools.api;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drools.service.DiscountService;

@RestController
@RequestMapping(value = "/api")
public class DemoApi {
	
	@Autowired
	private DiscountService discountService;
	
	
	@GetMapping("/execute")
	public void executeRule() throws Exception{
		System.out.println("*******IN EXECUTE RULE************");
		discountService.applyDiscount();
	}

}
