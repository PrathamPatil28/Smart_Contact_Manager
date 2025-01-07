package com.example.SCM;

import com.example.SCM.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private EmailService service;
	@Test
	 void sendEmailTest(){
          service.sendEmail("jaywantpatil455@gmail.com ",
				  "just Testing email Service work or not",
				  "this is SCM Project Working on email service");
	 }

}
