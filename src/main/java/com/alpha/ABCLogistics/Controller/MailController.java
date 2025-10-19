package com.alpha.ABCLogistics.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.ABCLogistics.Service.MailService;

@RestController
@RequestMapping("/admin")
public class MailController {
	@Autowired
	MailService mailservice;
	@PostMapping("/sendmail")
	public void sendMail(){
		String tomail="yogi@gmail.com";
		String subject="hello Spring Mail API";
		String contact="this simple mail";
		mailservice.sendMail(tomail,subject,contact);
	}
}
