package com.todoapp.msvc_notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class MsvcNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcNotificationApplication.class, args);
	}

}
