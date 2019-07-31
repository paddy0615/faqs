package com.example.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.example.demo.mapper") //扫描的mapper
@SpringBootApplication
/*@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@Configuration*/
public class DemoApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoApplication.class);
	}

	public static void main(String[] args) {
		//new SpringApplicationBuilder(DemoApplication.class).web(true).run(args);
		SpringApplication.run(DemoApplication.class, args);
	}


}
