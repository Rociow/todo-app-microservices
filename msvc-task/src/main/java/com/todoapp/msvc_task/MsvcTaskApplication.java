package com.todoapp.msvc_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.todoapp.msvc_task.client")
public class MsvcTaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcTaskApplication.class, args);
	}
}

