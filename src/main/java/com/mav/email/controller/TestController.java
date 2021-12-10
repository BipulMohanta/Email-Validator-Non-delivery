package com.mav.email.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author bipul.mohanta
 *
 */
@RestController
@RequestMapping(path = "test")
public class TestController {

	@GetMapping("testFunciton")
	public void testFunciton() {
		System.out.println("Controller is working !!!");
	}

}
