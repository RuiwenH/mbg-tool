package com.reven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.reven.dao")
@EnableTransactionManagement
public class SpringBootMBGToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMBGToolApplication.class, args);
	}

}
