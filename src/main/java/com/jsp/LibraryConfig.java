package com.jsp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc  // //used to enable Spring MVC configuration in a Java-based Spring project 

@ComponentScan(basePackages = "com.jsp")
public class LibraryConfig implements WebMvcConfigurer{
	
	//now creating viewResolver method to return all inside views it will go inside and check and when we want jsut tell spring ioc it will
	//return
	
	@Bean//to register as bean this method spring ioc create object and keep when it wants it takes from here
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver viewesolver=new InternalResourceViewResolver();
		//directly server goes to first webapp so we have to tell that beacuse its already server at webapp
		//so 1 level security we are giving just thats why we create inside webapp another folder views and kept in
		viewesolver.setPrefix("/WEB-INF/views/");
		viewesolver.setSuffix(".jsp");//here viewresolver will fetch all files inside prefix & suffix
		return viewesolver;
	}
	

	@Bean
	public EntityManager getEntityManager() {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("demo");
		return factory.createEntityManager();//returning same line why create another variable to more space
	}
	
	@Bean
	public EntityTransaction getEntityTransaction(EntityManager manager) {
		return manager.getTransaction();//here also returning at same line to avoid more space
	}
	
	
	// Static resources configuration 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}
