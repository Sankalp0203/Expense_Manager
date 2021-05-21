package com.example.Setu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ExpenseManagerApplication {

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(ExpenseManagerApplication.class, args);
		DbLoaderService dbLoaderService = appContext.getBean(DbLoaderService.class);
		dbLoaderService.populateDb();
	}

}
