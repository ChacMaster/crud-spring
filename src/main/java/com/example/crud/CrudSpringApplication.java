package com.example.crud;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.crud.util.ContextUtil;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.List;

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

	@Bean
	ServletRegistrationBean jsfServletRegistration (ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

		ServletRegistrationBean srb = new ServletRegistrationBean();
		srb.setServlet(new FacesServlet());
		srb.setUrlMappings(List.of("*.xhtml"));
		srb.setLoadOnStartup(1);
		return srb;
	}
}
