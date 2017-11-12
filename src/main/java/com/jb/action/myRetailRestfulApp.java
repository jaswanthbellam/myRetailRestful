package com.jb.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jb")
public class myRetailRestfulApp {

	public static void main(String[] args) {
		
		SpringApplication.run(myRetailRestfulApp.class, args);

	}

}
