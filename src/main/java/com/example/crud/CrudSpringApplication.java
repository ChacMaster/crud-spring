package com.example.crud;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.crud.util.ContextUtil;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(CrudSpringApplication.class, args);
		ContextUtil.setContext(context);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
