package com.lan.bing.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Combination(test = "cname", value = "aa")
public class AnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnotationApplication.class, args);
	}

}
